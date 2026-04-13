package com.panyou.md3todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panyou.md3todo.domain.model.Task
import com.panyou.md3todo.domain.repository.TaskRepository
import com.panyou.md3todo.network.LlmClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository,
    private val llmClient: LlmClient
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.observeAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    private val _aiSuggestion = MutableStateFlow<String?>(null)
    val aiSuggestion: StateFlow<String?> = _aiSuggestion
    
    private val _isLoadingAi = MutableStateFlow(false)
    val isLoadingAi: StateFlow<Boolean> = _isLoadingAi

    fun generateAiBreakdown(taskTitle: String) {
        viewModelScope.launch {
            _isLoadingAi.value = true
            // Fake the API key for demo purposes or use a real key if configured
            // We use a mock delay to simulate network since we don't have a real key configured in the app
            kotlinx.coroutines.delay(1500)
            _aiSuggestion.value = "1. Break down into smaller steps.\n2. Execute sequentially.\n3. Verify."
            // In a real app with a key: _aiSuggestion.value = llmClient.getTaskSuggestions(taskTitle, "YOUR_API_KEY")
            _isLoadingAi.value = false
        }
    }
    
    fun clearAiSuggestion() {
        _aiSuggestion.value = null
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            val task = Task(
                id = kotlinx.datetime.Clock.System.now().toEpochMilliseconds().toString(),
                title = title,
                createdAt = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
            )
            repository.insertTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }
    
    fun toggleTaskImportance(task: Task, isImportant: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isImportant = isImportant))
        }
    }
}