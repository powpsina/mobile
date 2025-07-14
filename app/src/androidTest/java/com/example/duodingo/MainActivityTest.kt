package com.example.duodingo

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationToTopicScreen() {
        composeTestRule.onNodeWithText("Duodingo").assertExists()
        composeTestRule.onNodeWithText("Животные").assertExists()

        composeTestRule.onNodeWithText("Животные").performClick()

        composeTestRule.onNodeWithText("Изучите слова:").assertExists()
        composeTestRule.onNodeWithText("Перейти к упражнению").assertExists()
    }

    @Test
    fun testNavigationToThreeWordsScreen() {
        composeTestRule.onNodeWithText("-ежедневный словарь-").performClick()

        composeTestRule.onNodeWithText("Переводчик слова").assertExists()
        composeTestRule.onNodeWithText("Сгенерировать случайное слово").assertExists()
    }
}