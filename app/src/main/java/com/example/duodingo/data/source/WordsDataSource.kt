package com.example.duodingo.data.source

import com.example.duodingo.data.repository.WordsRepository

class WordsDataSource {
    fun getTopics(): List<WordsRepository.Topic> = WordsRepository.topics
}