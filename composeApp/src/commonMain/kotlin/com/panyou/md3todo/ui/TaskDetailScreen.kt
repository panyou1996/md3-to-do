package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.panyou.md3todo.domain.model.Task
import kotlinx.coroutines.launch

@OptApi(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var aiSuggestion by remember { mutableStateOf<String?>(null) }
    var isLoadingAi by remember { mutableStateOf(false) }
    
    // Fake fetching task from ID
    val task = Task(
        id = taskId,
        title = "Complete the architecture review",
        description = "Review the current KMP setup and ensure Room and Ktor are correctly configured.",
        isImportant = true,
        createdAt = 0L
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { 
                    isLoadingAi = true
                    // Simulate AI call like FluxDO
                    coroutineScope.launch {
                        kotlinx.coroutines.delay(1500)
                        aiSuggestion = "1. Review Room KMP schemas.\n2. Validate Ktor network interceptors.\n3. Check DI graph in Koin."
                        isLoadingAi = false
                    }
                },
                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI") },
                text = { Text("AI Breakdown") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Text(task.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = task.description,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().weight(0.4f),
                label = { Text("Description (Markdown Supported)") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isLoadingAi) {
                CircularProgressIndicator()
                Text("AI is thinking...")
            } else if (aiSuggestion != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    modifier = Modifier.fillMaxWidth().weight(0.6f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("AI Suggested Subtasks:", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(aiSuggestion ?: "")
                    }
                }
            }
        }
    }
}