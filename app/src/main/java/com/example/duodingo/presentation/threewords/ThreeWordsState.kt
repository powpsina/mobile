package com.example.duodingo.presentation.threewords

data class ThreeWordsState(
    val userWord: String = "",
    val translation: String = "",
    val randomWord: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface ThreeWordsEvent {
    data class OnWordChanged(val word: String) : ThreeWordsEvent
    object OnRandomWordRequested : ThreeWordsEvent
    object OnTranslateRequested : ThreeWordsEvent
    object OnErrorShown : ThreeWordsEvent
}

sealed interface ThreeWordsEffect {
    data class ShowError(val message: String) : ThreeWordsEffect
    data class ShowTranslation(val translation: String) : ThreeWordsEffect
}