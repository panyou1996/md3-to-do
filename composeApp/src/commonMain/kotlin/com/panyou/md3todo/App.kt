package com.panyou.md3todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import org.koin.compose.KoinApplication
import com.panyou.md3todo.di.appModule
import com.panyou.md3todo.ui.*
import com.panyou.md3todo.domain.model.Task

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        val seedColor = Color(0xFF6750A4) // Primary Purple
        
        DynamicMaterialTheme(
            seedColor = seedColor,
            useDarkTheme = isSystemInDarkTheme(),
            animate = true // Enable fluid MD3 transitions
        ) {
            val currentScreen by NavigationManager.currentScreen
            
            // Fake data for UI demo
            val dummyTasks = listOf(
                Task("1", "Buy groceries", isCompleted = false, createdAt = 0L),
                Task("2", "Prepare Q2 presentation", isImportant = true, description = "**Crucial**: Needs the latest revenue data.", createdAt = 0L),
                Task("3", "Call the bank", isCompleted = true, createdAt = 0L)
            )

            when (val screen = currentScreen) {
                is Screen.MyDay -> {
                    MyDayScreen(
                        tasks = dummyTasks,
                        onMenuClick = { NavigationManager.navigateTo(Screen.Stats) }, // Temporarily map menu to stats for easy access
                        onTaskClick = { NavigationManager.navigateTo(Screen.TaskDetail(it.id)) },
                        onAddTask = { /* TODO */ }
                    )
                }
                is Screen.Stats -> {
                    StatsScreen(onBack = { NavigationManager.goBack() })
                }
                is Screen.TaskDetail -> {
                    TaskDetailScreen(
                        taskId = screen.taskId,
                        onBack = { NavigationManager.goBack() }
                    )
                }
                is Screen.Lists -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Lists Screen (Coming Soon)")
                    }
                }
            }
        }
    }
}