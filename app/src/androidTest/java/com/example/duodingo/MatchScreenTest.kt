package com.example.duodingo

import androidx.compose.ui.test.assertHasClickAction
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
class MatchScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.onNodeWithText("Животные").performClick()
        composeTestRule.onNodeWithText("Перейти к упражнению").performClick()
    }

    @Test
    fun testMatchWords() {
        composeTestRule.onNodeWithText("кот").performClick()
        composeTestRule.onNodeWithText("cat").performClick()

        composeTestRule.onNodeWithText("кот")
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun testBackNavigation() {
        composeTestRule.onNodeWithText("Назад к словам").performClick()

        composeTestRule.onNodeWithText("Изучите слова:").assertExists()
    }
}