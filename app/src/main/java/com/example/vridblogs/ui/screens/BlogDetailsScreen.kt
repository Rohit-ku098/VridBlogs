package com.example.vridblogs.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vridblogs.ui.components.TopBar
import com.example.vridblogs.ui.theme.DarkColors
import com.example.vridblogs.utils.formatDate
import com.example.vridblogs.viewmodel.MainViewModel

@Composable
fun BlogDetailsScreen(
    viewModel: MainViewModel,
    blogId: Int,
    onBack: () -> Unit
) {

    val posts = viewModel.blogPosts.collectAsState()
    val post = posts.value[blogId]

    val content = """
        <html>
        <head>
            <style>
                body {
                    background-color: #0F0F23;
                    color: #ffffff;
                    font-family: sans-serif;
                }
                a { color: #CAAA0B; }
                .wp-social-link {
                    fill: #CAAA0B;
                }
                li>svg { fill: #CAAA0B; }
            </style>
        </head>
        <body>
           ${post.content.rendered}
        </body>
    </html>
    """.trimIndent()


    Scaffold(
        topBar = {
            TopBar(
                title = "Vrid Blogs",
                showBackButton = true,
                onClickNavigation = onBack
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

            LazyColumn {
                item {
                    Text(
                        text = AnnotatedString.fromHtml(post.title.rendered),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = formatDate(post.date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    AndroidView(factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.domStorageEnabled = true
                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.loadsImagesAutomatically = true
                            settings.javaScriptEnabled = true

                            loadDataWithBaseURL(
                                null,
                                content,
                                "text/html",
                                "UTF-8",
                                null
                            )
                        }
                    })
                }
            }

        }
    }




}