package com.example.vridblogs.di

import android.content.Context
import androidx.room.Room
import com.example.vridblogs.data.local.AppDatabase
import com.example.vridblogs.data.local.BlogDao
import com.example.vridblogs.data.local.Convertors
import com.example.vridblogs.data.remote.ApiService
import com.example.vridblogs.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/wp-json/wp/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .addTypeConverter(Convertors())
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideBlogDao(appDatabase: AppDatabase): BlogDao {
        return appDatabase.blogDao()
    }


    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, blogDao: BlogDao): Repository {
        return Repository(apiService, blogDao)
    }
}