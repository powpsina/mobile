package com.example.duodingo.data

import com.example.duodingo.data.api.ApiClient
import com.example.duodingo.data.repository.WordsRepository.Topic
import com.example.duodingo.data.source.WordsDataSource
import javax.inject.Inject

interface WordRepository {
    suspend fun getTopics(): List<Topic>
    suspend fun getRandomWord(): String
    suspend fun getTranslation(word: String): String
}

class WordRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient,
    private val wordsDataSource: WordsDataSource
) : WordRepository {
    override suspend fun getTopics() = wordsDataSource.getTopics()
    override suspend fun getRandomWord() = apiClient.getRandomWord()
    override suspend fun getTranslation(word: String) = apiClient.getTranslation(word)
}