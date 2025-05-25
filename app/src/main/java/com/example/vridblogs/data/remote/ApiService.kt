package com.example.vridblogs.data.remote

import com.example.vridblogs.data.models.BlogPostItem
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("posts")
    suspend fun getBlogPosts(
        @QueryMap queries: Map<String, String>
    ): List<BlogPostItem>
}