package com.example.vridblogs.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.vridblogs.data.models.Content
import com.example.vridblogs.data.models.Excerpt
import com.example.vridblogs.data.models.Title
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class Convertors {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromIntList(list: List<Int>): String = json.encodeToString(list)

    @TypeConverter
    fun toIntList(jsonStr: String): List<Int> = json.decodeFromString(jsonStr)

    @TypeConverter
    fun fromContent(content: Content): String = json.encodeToString(content)

    @TypeConverter
    fun toContent(jsonStr: String): Content = json.decodeFromString(jsonStr)

    @TypeConverter
    fun fromExcerpt(excerpt: Excerpt): String = json.encodeToString(excerpt)

    @TypeConverter
    fun toExcerpt(jsonStr: String): Excerpt = json.decodeFromString(jsonStr)

    @TypeConverter
    fun fromTitle(title: Title): String = json.encodeToString(title)

    @TypeConverter
    fun toTitle(jsonStr: String): Title = json.decodeFromString(jsonStr)
}