package com.panyou.md3todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import org.koin.compose.KoinApplication
import com.panyou.md3todo.di.appModule

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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("MD3 Expressive ToDo\nPowered by Koin & MaterialKolor")
            }
        }
    }
}