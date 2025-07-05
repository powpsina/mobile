package com.example.duodingo.data.model

data class Word(
    val english: String,
    val russian: String
)

data class Topic(
    val title: String,
    val words: List<Word>
)