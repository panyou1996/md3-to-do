package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.panyou.md3todo.domain.model.Task
import com.panyou.md3todo.ui.viewmodel.TaskDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onBack: () -> Unit
) {
    val viewModel = koinViewModel<TaskDetailViewModel>(parameters = { parametersOf(taskId) })
    val task by viewModel.task.collectAsState()
    val isLoadingAi by viewModel.isLoadingAi.collectAsState()
    
    val currentTask = task ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Flag, contentDescription = "Priority") }
                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, contentDescription = "More") }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Label, contentDescription = "Tags") }
                    IconButton(onClick = { }) { Icon(Icons.Default.FormatListBulleted, contentDescription = "Subtasks") }
                    IconButton(onClick = { }) { Icon(Icons.Default.AttachFile, contentDescription = "Attach") }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { viewModel.runAiBreakdown("dummy-key") },
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        elevation = FloatingActionButtonDefaults.elevation()
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "AI")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).padding(horizontal = 20.dp).fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Inbox", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(text = currentTask.title, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextField(
                    value = currentTask.description, onValueChange = { }, placeholder = { Text("Add notes...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (isLoadingAi) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                
                // Real subtasks observation would go here
                currentTask.subtasks.forEach { subtask ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                        Checkbox(checked = subtask.isCompleted, onCheckedChange = {})
                        Text(subtask.title, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
