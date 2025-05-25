package com.example.vridblogs

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vridblogs.ui.Routes
import com.example.vridblogs.ui.components.BlogListingCard
import com.example.vridblogs.ui.components.TopBar
import com.example.vridblogs.ui.screens.BlogDetailsScreen
import com.example.vridblogs.ui.screens.HomeScreen
import com.example.vridblogs.ui.theme.VridBlogsTheme
import com.example.vridblogs.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                darkScrim = 1,
            ),
        )
        setContent {
            VridBlogsTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val viewModel = viewModel<MainViewModel>()
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {

        composable<Routes.Home> {
            HomeScreen(
                viewModel = viewModel,
                onClickPost = {
                    navController.navigate(Routes.BlogDetails(it))
                }
            )
        }

        composable<Routes.BlogDetails> {
            val blogId = it.arguments?.getInt("blogId")
            blogId?.let {
                BlogDetailsScreen(
                    viewModel = viewModel,
                    blogId = blogId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }

}
