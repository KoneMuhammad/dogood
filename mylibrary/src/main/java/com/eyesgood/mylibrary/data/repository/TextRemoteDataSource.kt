package com.eyesgood.mylibrary.data.repository

interface TextRemoteDataSource{

    suspend fun generateTextResponse(instruction: String): String?
}