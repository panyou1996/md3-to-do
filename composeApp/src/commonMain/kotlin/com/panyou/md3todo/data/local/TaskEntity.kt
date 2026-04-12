package com.panyou.md3todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val isImportant: Boolean,
    val myDayDate: Long?,
    val dueDate: Long?,
    val listId: String?,
    val createdAt: Long
)