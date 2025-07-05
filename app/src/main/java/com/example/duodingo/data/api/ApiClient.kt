package com.example.duodingo.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private suspend fun executeRequest(url: String): String {
        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Request failed: ${response.code}")
        return response.body?.string() ?: throw Exception("Empty response")
    }

    suspend fun getRandomWord(): String = withContext(Dispatchers.IO) {
        try {
            val json = executeRequest(ApiConstants.RANDOM_WORD_API_URL)
            JSONObject("{\"words\":$json}").getJSONArray("words").getString(0)
        } catch (e: Exception) {
            throw Exception("Failed to fetch random word: ${e.localizedMessage}")
        }
    }

    suspend fun getTranslation(word: String): String = withContext(Dispatchers.IO) {
        try {
            val encodedWord = URLEncoder.encode(word, "UTF-8")
            val url = ApiConstants.TRANSLATION_API_URL.format(encodedWord)
            val json = executeRequest(url)
            JSONObject(json).getJSONObject("responseData").getString("translatedText")
        } catch (e: Exception) {
            throw Exception("Translation error: ${e.localizedMessage}")
        }
    }
}