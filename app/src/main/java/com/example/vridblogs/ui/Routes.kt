package com.example.vridblogs.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object Home : Routes()

    @Serializable
    data class BlogDetails(val blogId: Int) : Routes()
}