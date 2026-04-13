package com.panyou.md3todo.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.panyou.md3todo.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBack: () -> Unit, taskViewModel: TaskViewModel) {
    val stats by taskViewModel.stats.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistics", fontWeight = FontWeight.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(240.dp)
            ) {
                item { StatCard("Daily Tasks", "${stats.dailyCompleted}", "+3") }
                item { StatCard("Focus Time", "${stats.focusTimeMinutes / 60}h ${stats.focusTimeMinutes % 60}m", "+15m") }
                item { StatCard("Total Tasks", "${stats.totalCompleted}", "") }
                item { StatCard("Streak", "${stats.streakDays} days", "🔥") }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Task Distribution", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                        val primaryColor = MaterialTheme.colorScheme.primary
                        val secondaryColor = MaterialTheme.colorScheme.secondary
                        val tertiaryColor = MaterialTheme.colorScheme.tertiary
                        
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 25.dp.toPx()
                            var currentAngle = -90f
                            
                            // Dynamically draw arcs based on stats.distribution
                            val colors = listOf(primaryColor, secondaryColor, tertiaryColor)
                            stats.distribution.forEachIndexed { index, percent ->
                                val sweep = percent * 360f
                                drawArc(
                                    color = colors[index % colors.size], 
                                    startAngle = currentAngle, 
                                    sweepAngle = sweep,
                                    useCenter = false, 
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                                currentAngle += sweep
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total", style = MaterialTheme.typography.labelMedium)
                            Text("100%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, trend: String) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                if (trend.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(trend, style = MaterialTheme.typography.labelSmall, color = if (trend.contains("+")) androidx.compose.ui.graphics.Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
