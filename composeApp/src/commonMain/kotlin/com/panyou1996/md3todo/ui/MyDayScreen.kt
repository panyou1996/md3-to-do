package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panyou.md3todo.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDayScreen(
    tasks: List<Task>,
    onMenuClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onAddTask: (String) -> Unit,
    onTaskCheckChanged: (Task, Boolean) -> Unit
) {
    var newTaskTitle by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { 
                    Text(
                        "Inbox", 
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .imePadding()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        placeholder = { Text("Add a task to Inbox...") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        ),
                        singleLine = true
                    )
                    if (newTaskTitle.isNotBlank()) {
                        IconButton(onClick = {
                            onAddTask(newTaskTitle)
                            newTaskTitle = ""
                        }) {
                            Icon(Icons.Default.Add, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            val todayTasks = tasks.filter { !it.isCompleted }
            val completedTasks = tasks.filter { it.isCompleted }
            
            if (todayTasks.isNotEmpty()) {
                item { SectionHeader("Today", todayTasks.size) }
                items(todayTasks) { task ->
                    TaskCard(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onCheckChanged = { onTaskCheckChanged(task, it) }
                    )
                }
            }
            
            if (completedTasks.isNotEmpty()) {
                item { SectionHeader("Completed", completedTasks.size) }
                items(completedTasks) { task ->
                    TaskCard(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onCheckChanged = { onTaskCheckChanged(task, it) }
                    )
                }
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun SectionHeader(title: String, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = "$count",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}
