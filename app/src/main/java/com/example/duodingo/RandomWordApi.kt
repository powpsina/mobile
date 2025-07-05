package com.example.duodingo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

object RandomWordApi {
    private const val API_URL = "https://random-word-api.herokuapp.com/word?number=1"

    suspend fun getRandomWord(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = URL(API_URL).readText()
                JSONArray(response).getString(0)
            } catch (e: Exception) {
                "error" // или обработай ошибку иначе
            }
        }
    }
}