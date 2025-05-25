package com.example.vridblogs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vridblogs.ui.components.BlogListingCard
import com.example.vridblogs.ui.components.TopBar
import com.example.vridblogs.viewmodel.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


//@Composable
//fun LazyListState.OnBottomReached(
//    buffer: Int = 2,
//    onLoadMore: () -> Unit
//) {
//    val shouldLoadMore = remember {
//        derivedStateOf {
//            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
//                ?: return@derivedStateOf true
//
//            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
//        }
//    }
//
//    LaunchedEffect(shouldLoadMore) {
//        snapshotFlow { shouldLoadMore.value }
//            .distinctUntilChanged()
//            .collect { if (it) onLoadMore() }
//    }
//}


@Composable
fun LazyGridState.OnBottomReached(
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect { if (it) onLoadMore() }
    }
}



@Composable
fun HomeScreen(
     viewModel: MainViewModel,
     onClickPost: (blogId: Int) -> Unit

) {

    val posts by viewModel.blogPosts.collectAsState()
    val lazyGridState = rememberLazyGridState()

    lazyGridState.OnBottomReached {
        viewModel.getBlogPosts()
    }


    Scaffold(
        topBar = {
            TopBar(
                title = "VridBlogs",
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(250.dp),
                state = lazyGridState
            ) {
                itemsIndexed(posts) { index, post ->
                    BlogListingCard(
                        title = post.title.rendered,
                        description = post.excerpt.rendered,
                        imageUrl = post.jetpack_featured_media_url,
                        uploadDate = post.date,
                        onClick = {
                            onClickPost(index)
                        }
                    )

                }

                item {
                    if(viewModel.isLoading.collectAsState().value) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = "Loading...")
                        }
                    }
                }
            }
        }

    }


}