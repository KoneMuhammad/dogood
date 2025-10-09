package com.eyesgood.mylibrary.data.repository

import jakarta.inject.Inject


class TextRepositoryImpl @Inject constructor(val textRemoteDataSource: TextRemoteDataSource): TextRepository {
    override suspend fun generateResponse(instruction: String): String? {
        val lLMResponse = textRemoteDataSource.generateTextResponse(instruction)
        return lLMResponse
    }
}