package com.eyesgood.mylibrary.data

import kotlinx.serialization.Serializable


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
