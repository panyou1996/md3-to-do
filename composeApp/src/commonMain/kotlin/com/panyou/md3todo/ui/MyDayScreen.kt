package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.panyou.md3todo.domain.model.Task
import kotlinx.coroutines.launch

@OptApi(ExperimentalMaterial3Api::class)
@Composable
fun MyDayScreen(
    tasks: List<Task>,
    onMenuClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onAddTask: (String) -> Unit
) {
    var newTaskTitle by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { 
                    Column {
                        Text("My Day", fontWeight = FontWeight.ExtraBold)
                        Text(
                            "Sunday, April 12", 
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    if (newTaskTitle.isNotBlank()) {
                        onAddTask(newTaskTitle)
                        newTaskTitle = ""
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    placeholder = { Text("Add a task...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    onClick = { onTaskClick(task) },
                    onCheckChanged = { /* TODO */ }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) } // Padding for bottom input
        }
    }
}