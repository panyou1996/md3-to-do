package com.panyou.md3todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panyou.md3todo.data.repository.TaskRepository
import com.panyou.md3todo.domain.model.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TaskStats(
    val dailyCompleted: Int,
    val totalCompleted: Int,
    val focusTimeMinutes: Int,
    val streakDays: Int,
    val distribution: List<Float> // Simplified distribution for Donut Chart
)

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.observeInboxTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats: StateFlow<TaskStats> = tasks.map { allTasks ->
        val completed = allTasks.filter { it.isCompleted }
        TaskStats(
            dailyCompleted = completed.size, // Simplified for demo
            totalCompleted = completed.size,
            focusTimeMinutes = completed.size * 25, // Mock focus time logic
            streakDays = 15,
            distribution = listOf(0.6f, 0.25f, 0.15f) // Mock distribution percentages
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskStats(0, 0, 0, 0, listOf(1f, 0f, 0f)))

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(title)
        }
    }

    fun toggleTask(id: String) {
        viewModelScope.launch {
            repository.toggleTask(id)
        }
    }
}
