package com.example.duodingo

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.duodingo.presentation.threewords.ThreeWordsScreen
import com.example.duodingo.presentation.threewords.ThreeWordsState
import com.example.duodingo.presentation.threewords.ThreeWordsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class ThreeWordsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screen_displays_correct_initial_state() {
        val state = ThreeWordsState()
        composeTestRule.setContent {
            ThreeWordsScreen(onBack = {}, viewModel = mockViewModel(state))
        }

        composeTestRule.onNodeWithText("Сгенерировать случайное слово").assertExists()
        composeTestRule.onNodeWithText("Перевести").assertExists().assertIsNotEnabled()
    }

    @Test
    fun word_input_updates_text_field() {
        val state = ThreeWordsState()
        composeTestRule.setContent {
            ThreeWordsScreen(onBack = {}, viewModel = mockViewModel(state))
        }

        composeTestRule.onNodeWithText("Введите английское слово")
            .performTextInput("hello")
    }

    @Test
    fun translate_button_enabled_when_word_entered() {
        val state = ThreeWordsState(userWord = "test")
        composeTestRule.setContent {
            ThreeWordsScreen(onBack = {}, viewModel = mockViewModel(state))
        }

        composeTestRule.onNodeWithText("Перевести").assertIsEnabled()
    }

    private fun mockViewModel(state: ThreeWordsState): ThreeWordsViewModel {
        return object : ThreeWordsViewModel(mock()) {
            override val state = MutableStateFlow(state)
        }
    }
}