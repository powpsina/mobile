package com.example.duodingo

import com.example.duodingo.data.WordRepository
import com.example.duodingo.presentation.threewords.ThreeWordsEvent
import com.example.duodingo.presentation.threewords.ThreeWordsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ThreeWordsViewModelTest {

    private lateinit var viewModel: ThreeWordsViewModel
    private val repository: WordRepository = mock()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ThreeWordsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val initialState = viewModel.state.value
        assertTrue(initialState.userWord.isEmpty())
        assertTrue(initialState.translation.isEmpty())
        assertTrue(initialState.randomWord.isEmpty())
        assertFalse(initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun `on word changed updates state`() = runTest {
        val testWord = "hello"
        viewModel.onEvent(ThreeWordsEvent.OnWordChanged(testWord))

        assertEquals(testWord, viewModel.state.value.userWord)
    }

    @Test
    fun `fetch random word success`() = runTest {
        val testWord = "random"
        whenever(repository.getRandomWord()).thenReturn(testWord)

        viewModel.onEvent(ThreeWordsEvent.OnRandomWordRequested)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(testWord, viewModel.state.value.randomWord)
        assertEquals(testWord, viewModel.state.value.userWord)
        assertFalse(viewModel.state.value.isLoading)
    }


    @Test
    fun `translate word success`() = runTest {
        val testWord = "hello"
        val translation = "привет"
        whenever(repository.getTranslation(testWord)).thenReturn(translation)

        viewModel.onEvent(ThreeWordsEvent.OnWordChanged(testWord))
        viewModel.onEvent(ThreeWordsEvent.OnTranslateRequested)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(translation, viewModel.state.value.translation)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `translate empty word does nothing`() = runTest {
        viewModel.onEvent(ThreeWordsEvent.OnTranslateRequested)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.state.value.translation.isEmpty())
    }
}