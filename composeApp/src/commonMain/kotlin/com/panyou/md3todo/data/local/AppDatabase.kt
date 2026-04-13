package com.panyou.md3todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class, SubtaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
