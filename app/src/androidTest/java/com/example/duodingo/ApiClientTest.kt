package com.example.duodingo

import com.example.duodingo.data.api.ApiClient
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test

class ApiClientTest {
    private val client = ApiClient(OkHttpClient())

    @Test
    fun getRandomWord_returnsNonEmptyString() = runTest {
        val word = client.getRandomWord()
        assertTrue(word.isNotBlank())
    }

    @Test
    fun getTranslation_returnsValidTranslation() = runTest {
        val translation = client.getTranslation("hello")
        assertTrue(translation.isNotBlank())
    }
}