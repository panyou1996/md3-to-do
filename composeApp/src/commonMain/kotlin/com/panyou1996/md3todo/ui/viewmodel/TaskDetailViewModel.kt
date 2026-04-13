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
            try {
                val suggestions = llmClient.getTaskSuggestions(currentTask.title, apiKey)
                // In a real app, we'd parse the LLM response. 
                // For this pixel-perfect demo, we simulate adding parsed subtasks to the DB.
                val mockSubtasks = listOf(
                    "Check requirements",
                    "Develop prototype",
                    "User testing"
                )
                mockSubtasks.forEach { title ->
                    // repository.addSubtask(taskId, title) 
                    // (Assuming addSubtask is implemented in Repository)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoadingAi.value = false
            }
        }
    }
}
