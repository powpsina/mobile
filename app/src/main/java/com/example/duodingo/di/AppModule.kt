package com.example.duodingo.di

import com.example.duodingo.data.WordRepository
import com.example.duodingo.data.WordRepositoryImpl
import com.example.duodingo.data.api.ApiClient
import com.example.duodingo.data.source.WordsDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWordRepository(
        wordRepositoryImpl: WordRepositoryImpl
    ): WordRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideApiClient(okHttpClient: OkHttpClient): ApiClient = ApiClient(okHttpClient)

    @Provides
    @Singleton
    fun provideWordsDataSource(): WordsDataSource = WordsDataSource()

    @Provides
    @Singleton
    fun provideWordRepositoryImpl(
        apiClient: ApiClient,
        wordsDataSource: WordsDataSource
    ): WordRepositoryImpl = WordRepositoryImpl(apiClient, wordsDataSource)
}