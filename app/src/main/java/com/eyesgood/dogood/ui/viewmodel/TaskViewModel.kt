package com.eyesgood.dogood.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyesgood.mylibrary.data.ModelTaskMessage
import com.eyesgood.mylibrary.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


data class TaskItemsUiState(
    val taskMessage: ModelTaskMessage = ModelTaskMessage(
        id = 0,
        type_of_task = "",
        task_message = "",
        emblem_url = ""
    ),
    val message: String = ""
)


@HiltViewModel
class TaskViewModel@Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    var taskItemsUiState by mutableStateOf(TaskItemsUiState())
        private set

    var fetchJob: Job? = null

    fun getTaskMessage(){
        try {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                val taskMessage = taskRepository.getMessageData()
                taskItemsUiState = taskItemsUiState.copy(taskMessage = taskMessage)

            }

        }catch (error: IOException){
            taskItemsUiState = taskItemsUiState.copy(message = "bit of an error count 3... seconds then click the button")

        }
    }
}