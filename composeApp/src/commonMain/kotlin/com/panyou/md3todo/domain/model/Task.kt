package com.panyou.md3todo.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String = "", // To be rendered as Markdown
    val isCompleted: Boolean = false,
    val isImportant: Boolean = false,
    val myDayDate: Long? = null, // Unix timestamp indicating if it's in "My Day"
    val dueDate: Long? = null, // Reminders / Due
    val listId: String? = null, // Belongs to a specific folder/list
    val createdAt: Long
)