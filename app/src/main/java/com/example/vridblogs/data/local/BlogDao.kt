package com.example.vridblogs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.vridblogs.data.models.BlogPostItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {
    @Query("SELECT * FROM blog_posts ORDER BY date DESC LIMIT :limit OFFSET :offset")
    fun getBlogPosts(limit: Int, offset: Int): Flow<List<BlogPostItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogPosts(blogPosts: List<BlogPostItem>)

    @Query("DELETE FROM blog_posts")
    suspend fun deleteBlogPosts()

    @Transaction
    suspend fun updateBlogPosts(blogPosts: List<BlogPostItem>) {
        deleteBlogPosts()
        insertBlogPosts(blogPosts)
    }

}