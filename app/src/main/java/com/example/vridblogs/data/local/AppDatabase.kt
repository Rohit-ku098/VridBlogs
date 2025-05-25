package com.example.vridblogs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vridblogs.data.models.BlogPostItem

@Database(entities = [BlogPostItem::class], version = 1)
@TypeConverters(Convertors::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun blogDao(): BlogDao
}