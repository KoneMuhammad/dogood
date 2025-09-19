package com.kone.fullbodymuscle.data.dataSource

import com.kone.fullbodymuscle.data.models.ModelTaskMessage
import com.kone.fullbodymuscle.data.models.ModelTaskWeather
import kotlinx.coroutines.flow.Flow

interface TaskNetworkDataSource {

   suspend fun getMessageData() : ModelTaskMessage
}