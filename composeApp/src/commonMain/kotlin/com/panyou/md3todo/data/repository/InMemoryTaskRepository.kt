package com.panyou.md3todo.data.repository

import com.panyou.md3todo.domain.model.Task
import com.panyou.md3todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryTaskRepository : TaskRepository {
    private val tasksFlow = MutableStateFlow<List<Task>>(
        listOf(
            Task("1", "Buy groceries", isCompleted = false, createdAt = 0L),
            Task("2", "Prepare Q2 presentation", isImportant = true, description = "**Crucial**: Needs the latest revenue data.", createdAt = 0L, myDayDate = 123L),
            Task("3", "Call the bank", isCompleted = true, createdAt = 0L),
            Task("4", "Schedule AI review", isCompleted = false, createdAt = 0L, myDayDate = 123L)
        )
    )

    override fun observeAllTasks(): Flow<List<Task>> = tasksFlow.asStateFlow()

    override suspend fun insertTask(task: Task) {
        tasksFlow.update { it + task }
    }

    override suspend fun updateTask(task: Task) {
        tasksFlow.update { list ->
            list.map { if (it.id == task.id) task else it }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        tasksFlow.update { list ->
            list.filter { it.id != taskId }
        }
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return tasksFlow.value.find { it.id == taskId }
    }
}