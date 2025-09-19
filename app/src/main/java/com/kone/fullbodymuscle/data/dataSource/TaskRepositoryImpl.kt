package com.kone.fullbodymuscle.data.dataSource

import com.kone.fullbodymuscle.data.models.ModelTaskMessage
import com.kone.fullbodymuscle.data.models.ModelTaskWeather
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(private val taskNetworkDataSource: TaskNetworkDataSource) :
       TaskRepository {

    override suspend fun getMessageData(): ModelTaskMessage =
        taskNetworkDataSource.getMessageData()

}


