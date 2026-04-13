package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Inbox", 
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Search") }
                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, contentDescription = "More") }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle Fab */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp).size(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            val pendingTasks = tasks.filter { !it.isCompleted }
            val completedTasks = tasks.filter { it.isCompleted }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }

            if (pendingTasks.isNotEmpty()) {
                item { SectionHeader("Today", pendingTasks.size) }
                items(pendingTasks) { task ->
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
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = "$count",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}
