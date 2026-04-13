package com.panyou.md3todo.data.repository

import com.panyou.md3todo.data.local.SubtaskEntity
import com.panyou.md3todo.data.local.TaskDao
import com.panyou.md3todo.data.local.TaskEntity
import com.panyou.md3todo.domain.model.Priority
import com.panyou.md3todo.domain.model.Subtask
import com.panyou.md3todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {

    fun observeInboxTasks(): Flow<List<Task>> {
        return taskDao.observeAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun observeTaskWithSubtasks(taskId: String): Flow<Task?> {
        val taskFlow = taskDao.observeAllTasks().map { list -> list.find { it.id == taskId } }
        val subtasksFlow = taskDao.observeSubtasks(taskId)
        
        return combine(taskFlow, subtasksFlow) { taskEntity, subEntities ->
            taskEntity?.toDomain()?.copy(
                subtasks = subEntities.map { it.toDomain() }
            )
        }
    }

    suspend fun addTask(title: String) {
        val newTask = TaskEntity(
            id = uuid(), 
            title = title,
            description = "",
            isCompleted = false,
            priority = Priority.NONE.level,
            tags = "",
            myDayDate = null,
            dueDate = null,
            listId = "inbox",
            createdAt = currentTimeMillis()
        )
        taskDao.insertTask(newTask)
    }

    suspend fun toggleTask(id: String) {
        taskDao.getTaskById(id)?.let {
            taskDao.updateTask(it.copy(isCompleted = !it.isCompleted))
        }
    }

    // Helpers
    private fun TaskEntity.toDomain() = Task(
        id = id, title = title, description = description, isCompleted = isCompleted,
        priority = Priority.values().first { it.level == priority },
        tags = if (tags.isEmpty()) emptyList() else tags.split(","),
        createdAt = createdAt, myDayDate = myDayDate, dueDate = dueDate, listId = listId
    )

    private fun SubtaskEntity.toDomain() = Subtask(
        id = id, taskId = taskId, title = title, isCompleted = isCompleted, createdAt = createdAt
    )

    private fun uuid() = kotlinx.datetime.Clock.System.now().toEpochMilliseconds().toString() // Simple UUID for now
    private fun currentTimeMillis() = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
}
