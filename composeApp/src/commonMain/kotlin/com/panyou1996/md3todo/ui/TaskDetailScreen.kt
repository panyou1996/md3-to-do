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
import com.panyou.md3todo.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onBack: () -> Unit,
    taskViewModel: TaskViewModel
) {
    val tasks by taskViewModel.tasks.collectAsState()
    val task = tasks.find { it.id == taskId } ?: return
    
    val coroutineScope = rememberCoroutineScope()
    var aiSuggestion by remember { mutableStateOf<String?>(null) }
    var isLoadingAi by remember { mutableStateOf(false) }

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
                        onClick = { 
                            isLoadingAi = true
                            coroutineScope.launch {
                                kotlinx.coroutines.delay(1000)
                                aiSuggestion = "• Buy reusable bags\n• Check for discount coupons\n• Buy bottled water"
                                isLoadingAi = false
                            }
                        },
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
                
                Text(text = task.title, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextField(
                    value = task.description, onValueChange = { }, placeholder = { Text("Add notes...") },
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
                
                if (aiSuggestion != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("AI Suggestions", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(aiSuggestion!!, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
