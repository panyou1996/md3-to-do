package com.panyou.md3todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                Task("2", "Prepare Q2 presentation", isImportant = true, description = "**Crucial**: Needs the latest revenue data.", createdAt = 0L, myDayDate = 123L),
                Task("3", "Call the bank", isCompleted = true, createdAt = 0L),
                Task("4", "Schedule AI review", isCompleted = false, createdAt = 0L, myDayDate = 123L)
            )

            Scaffold(
                bottomBar = {
                    NavigationBar(
                        tonalElevation = 8.dp,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        NavigationBarItem(
                            selected = currentScreen is Screen.MyDay,
                            onClick = { NavigationManager.navigateTo(Screen.MyDay) },
                            icon = { Icon(Icons.Default.List, contentDescription = "Tasks") },
                            label = { Text("Tasks") }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
                            label = { Text("Calendar") }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = { Icon(Icons.Default.GridView, contentDescription = "Matrix") },
                            label = { Text("Matrix") }
                        )
                        NavigationBarItem(
                            selected = currentScreen is Screen.Stats,
                            onClick = { NavigationManager.navigateTo(Screen.Stats) },
                            icon = { Icon(Icons.Default.PieChart, contentDescription = "Stats") },
                            label = { Text("Stats") }
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                            label = { Text("Profile") }
                        )
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding).fillMaxSize()) {
                    when (val screen = currentScreen) {
                        is Screen.MyDay -> {
                            MyDayScreen(
                                tasks = dummyTasks,
                                onMenuClick = { /* TODO: Open Drawer */ },
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
                                Text("Lists Screen")
                            }
                        }
                    }
                }
            }
        }
    }
}
