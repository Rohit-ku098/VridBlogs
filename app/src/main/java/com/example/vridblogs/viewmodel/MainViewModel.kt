package com.example.vridblogs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vridblogs.common.ApiResponse
import com.example.vridblogs.data.models.BlogPostItem
import com.example.vridblogs.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val repository: Repository) : ViewModel() {
    private val _blogPosts = MutableStateFlow<List<BlogPostItem>>(emptyList())
    val blogPosts = _blogPosts.asStateFlow()

    private val currentPage = MutableStateFlow<Int>(1)
    private val perPage = 10


    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow<Boolean>(false)
    val isError = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadInitialPosts()
    }


    fun loadInitialPosts() {
        viewModelScope.launch {
            repository.getBlogPosts(
                perPage,
                1
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        _blogPosts.value = it.data
                        _isLoading.value = false
                        _isError.value = false
                        currentPage.value = 1
                        Log.d("MainViewModel", it.data.toString())
                    }
                    is ApiResponse.Error -> {
                        _isLoading.value = false
                        _isError.value = true
                        Log.d("MainViewModel", it.message)
                        }
                    is ApiResponse.Loading -> {
                        _isLoading.value = true
                        _isError.value = false
                        Log.d("MainViewModel", "Loading")
                    }
                }
            }
        }
    }

    fun getBlogPosts() {
        // check if we have already fetched the last page
        if(_isLoading.value) return
        val nextPage = currentPage.value + 1

        viewModelScope.launch {
            repository.getBlogPosts(
                perPage,
                nextPage
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        _blogPosts.update { data ->
                            data + it.data as List<BlogPostItem>
                        }
                        _isLoading.value = false
                        _isError.value = false
                        _errorMessage.value = ""
                        currentPage.value = nextPage
                        Log.d("MainViewModel", it.data.toString())
                    }
                    is ApiResponse.Error -> {
                        _isLoading.value = false
                        _isError.value = true
                        _errorMessage.value = it.message
                        Log.d("MainViewModel", it.message)
                    }
                    is ApiResponse.Loading -> {
                        _isLoading.value = true
                        _isError.value = false
                        _errorMessage.value = ""
                        Log.d("MainViewModel", "Loading")
                    }
                }
            }
        }
    }

}