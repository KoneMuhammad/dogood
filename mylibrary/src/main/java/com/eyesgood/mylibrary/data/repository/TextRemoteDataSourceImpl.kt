package com.eyesgood.mylibrary.data.repository

import com.google.firebase.ai.GenerativeModel
import jakarta.inject.Inject

class TextRemoteDataSourceImpl@Inject constructor(private val generativeModel: GenerativeModel): TextRemoteDataSource {
    override suspend fun generateTextResponse(instruction: String): String? {
        val lLMResponse = generativeModel.generateContent(instruction)
        return lLMResponse.text
    }
}