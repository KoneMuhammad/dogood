package com.kone.fullbodymuscle.data.models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ModelTaskWeather(
    val id: Long,
    val weather_type: String,
    val video_url: String,
)


@Serializable
data class ModelTaskMessage(
    val id: Long,
    val type_of_task: String,
    val task_message: String,
    val emblem_url: String
)

//amazing for backend works