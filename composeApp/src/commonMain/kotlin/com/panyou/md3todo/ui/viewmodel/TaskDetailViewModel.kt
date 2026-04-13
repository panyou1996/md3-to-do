package com.panyou.md3todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panyou.md3todo.data.repository.TaskRepository
import com.panyou.md3todo.domain.model.Task
import com.panyou.md3todo.network.LlmClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val repository: TaskRepository,
    private val llmClient: LlmClient,
    private val taskId: String
) : ViewModel() {

    val task: StateFlow<Task?> = repository.observeTaskWithSubtasks(taskId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _isLoadingAi = MutableStateFlow(false)
    val isLoadingAi = _isLoadingAi.asStateFlow()

    fun runAiBreakdown(apiKey: String) {
        val currentTask = task.value ?: return
        viewModelScope.launch {
            _isLoadingAi.value = true
            val suggestions = llmClient.getTaskSuggestions(currentTask.title, apiKey)
            // Logic to parse suggestions and insert subtasks would go here
            _isLoadingAi.value = false
        }
    }
}
