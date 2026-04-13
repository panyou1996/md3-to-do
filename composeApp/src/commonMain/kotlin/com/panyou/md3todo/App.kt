package com.panyou.md3todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import com.panyou.md3todo.di.appModule
import com.panyou.md3todo.ui.*
import com.panyou.md3todo.ui.viewmodel.TaskViewModel
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
            val taskViewModel = koinViewModel<TaskViewModel>()
            val tasks by taskViewModel.tasks.collectAsState()

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
                                tasks = tasks,
                                onMenuClick = { /* TODO: Open Drawer */ },
                                onTaskClick = { NavigationManager.navigateTo(Screen.TaskDetail(it.id)) },
                                onAddTask = { taskViewModel.addTask(it) },
                                onTaskCheckChanged = { task, isCompleted -> taskViewModel.toggleTask(task.id) }
                            )
                        }
                        is Screen.Stats -> {
                            StatsScreen(onBack = { NavigationManager.goBack() }, taskViewModel = taskViewModel)
                        }
                        is Screen.TaskDetail -> {
                            TaskDetailScreen(
                                taskId = screen.taskId,
                                onBack = { NavigationManager.goBack() },
                                taskViewModel = taskViewModel
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
