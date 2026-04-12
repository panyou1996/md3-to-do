package com.panyou.md3todo.domain.model

data class Subtask(
    val id: String,
    val taskId: String,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: Long
)