package com.example.vridblogs.data.models

import androidx.room.Entity

@Entity(tableName = "blog_posts", primaryKeys = ["id"])
data class BlogPostItem(
    val id: Int,
    val author: Int,
    val categories: List<Int>,
    val content: Content,
    val date: String,
    val date_gmt: String,
    val excerpt: Excerpt,
    val jetpack_featured_media_url: String,
    val link: String,
    val modified: String,
    val modified_gmt: String,
    val slug: String,
    val status: String,
    val title: Title,
    val type: String
)