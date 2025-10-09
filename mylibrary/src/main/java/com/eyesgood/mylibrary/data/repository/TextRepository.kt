package com.eyesgood.mylibrary.data.repository

 interface TextRepository {

    suspend fun generateResponse(instruction: String): String?

}