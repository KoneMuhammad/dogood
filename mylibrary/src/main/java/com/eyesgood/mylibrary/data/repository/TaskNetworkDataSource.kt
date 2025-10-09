package com.eyesgood.mylibrary.data.repository

import com.eyesgood.mylibrary.data.ModelTaskMessage

interface TaskNetworkDataSource {
    suspend fun getMessageData() : ModelTaskMessage
}
