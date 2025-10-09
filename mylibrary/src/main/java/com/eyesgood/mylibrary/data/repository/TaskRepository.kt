package com.eyesgood.mylibrary.data.repository

import com.eyesgood.mylibrary.data.ModelTaskMessage

interface TaskRepository {
    suspend fun getMessageData() : ModelTaskMessage
}