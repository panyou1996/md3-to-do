package com.panyou.md3todo.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class Priority(val level: Int) {
    NONE(0), LOW(1), MEDIUM(2), HIGH(3)
}

data class Task(
    val id: String,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.NONE,
    val tags: List<String> = emptyList(),
    val subtasks: List<Subtask> = emptyList(),
    val myDayDate: Long? = null,
    val dueDate: Long? = null,
    val listId: String? = null,
    val createdAt: Long
) {
    fun isToday(): Boolean {
        if (dueDate == null) return false
        val now = kotlinx.datetime.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val taskDate = Instant.fromEpochMilliseconds(dueDate).toLocalDateTime(TimeZone.currentSystemDefault())
        return now.date == taskDate.date
    }
}
