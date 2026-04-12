package com.panyou.md3todo.domain.model

data class TaskList(
    val id: String,
    val name: String,
    val colorHex: Long? = null, // MD3 dynamic color seed for this specific list
    val iconName: String? = null,
    val createdAt: Long
)