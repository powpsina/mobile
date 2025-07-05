package com.example.duodingo.presentation.threewords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duodingo.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ThreeWordsViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ThreeWordsState())
    open val state: StateFlow<ThreeWordsState> = _state

    private val _effect = Channel<ThreeWordsEffect>()
    val effect = _effect.receiveAsFlow()

    private var currentJob: kotlinx.coroutines.Job? = null

    fun onEvent(event: ThreeWordsEvent) {
        when (event) {
            is ThreeWordsEvent.OnWordChanged -> handleWordChanged(event.word)
            ThreeWordsEvent.OnRandomWordRequested -> fetchRandomWord()
            ThreeWordsEvent.OnTranslateRequested -> translate()
            ThreeWordsEvent.OnErrorShown -> clearError()
        }
    }

    private fun handleWordChanged(word: String) {
        _state.value = _state.value.copy(
            userWord = word,
            translation = if (word != _state.value.randomWord) "" else _state.value.translation
        )
    }

    private fun fetchRandomWord() {
        if (_state.value.isLoading) return

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                translation = ""
            )
            try {
                val word = repository.getRandomWord()
                _state.value = _state.value.copy(
                    randomWord = word,
                    userWord = word,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Не удалось получить слово: ${e.message}"
                )
                _effect.send(ThreeWordsEffect.ShowError("Ошибка: ${e.localizedMessage}"))
            }
        }
    }

    private fun translate() {
        if (_state.value.isLoading || _state.value.userWord.isBlank()) return

        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val translation = repository.getTranslation(_state.value.userWord)
                _state.value = _state.value.copy(
                    translation = translation,
                    isLoading = false
                )
                _effect.send(ThreeWordsEffect.ShowTranslation(translation))
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка перевода: ${e.message}"
                )
                _effect.send(ThreeWordsEffect.ShowError("Ошибка перевода: ${e.localizedMessage}"))
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}