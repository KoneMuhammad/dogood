package com.eyesgood.mylibrary.data.repository

import com.eyesgood.mylibrary.data.ModelTaskMessage
import jakarta.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskNetworkDataSource: TaskNetworkDataSource) :
    TaskRepository {

    override suspend fun getMessageData(): ModelTaskMessage =
        taskNetworkDataSource.getMessageData()

}