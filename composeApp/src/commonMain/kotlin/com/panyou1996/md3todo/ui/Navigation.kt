package com.panyou.md3todo.ui

import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object MyDay : Screen()
    object Lists : Screen()
    object Stats : Screen()
    data class TaskDetail(val taskId: String) : Screen()
}

object NavigationManager {
    val currentScreen = mutableStateOf<Screen>(Screen.MyDay)

    fun navigateTo(screen: Screen) {
        currentScreen.value = screen
    }
    
    fun goBack() {
        currentScreen.value = Screen.MyDay
    }
}
