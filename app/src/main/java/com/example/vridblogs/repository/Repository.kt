package com.example.vridblogs.repository

import android.util.Log
import com.example.vridblogs.common.ApiResponse
import com.example.vridblogs.data.local.BlogDao
import com.example.vridblogs.data.models.BlogPostItem
import com.example.vridblogs.data.remote.ApiService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService, private val blogDao: BlogDao) {

    fun getBlogPosts(limit: Int, page: Int): Flow<ApiResponse<List<BlogPostItem>>> = callbackFlow {
        trySend(ApiResponse.Loading)
        try {
            apiService.getBlogPosts(
                mapOf(
                    "per_page" to limit.toString(),
                    "page" to page.toString()
                )
            ).let { blogPosts ->
                trySend(ApiResponse.Success(blogPosts))

                if(page == 1) {
                    blogDao.deleteBlogPosts()
                    blogDao.insertBlogPosts(blogPosts)
                }
                else {
                    blogDao.insertBlogPosts(blogPosts)
                }
            }

        } catch (e: Exception) {
            val cachedBlogs = blogDao.getBlogPosts(limit, page*limit - limit).firstOrNull()
            if (cachedBlogs.isNullOrEmpty()) {
                trySend(ApiResponse.Error(e.message ?: "Unknown error"))
            }
            else {
                trySend(ApiResponse.Success(cachedBlogs))
            }
        } finally {
            awaitClose { close() }
        }
    }
}