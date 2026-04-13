package com.panyou.md3todo.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panyou.md3todo.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBack: () -> Unit, taskViewModel: TaskViewModel) {
    val stats by taskViewModel.stats.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus Statistics", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = { }) { Icon(Icons.Default.Share, contentDescription = "Share") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Metrics Grid - Pixel Perfect Dida Style
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(220.dp)
            ) {
                item { MetricItem("Today's Tasks", "${stats.dailyCompleted}", "+3", MaterialTheme.colorScheme.primary) }
                item { MetricItem("Focus Time", "${stats.focusTimeMinutes}m", "+15m", androidx.compose.ui.graphics.Color(0xFF2196F3)) }
                item { MetricItem("Total Tasks", "${stats.totalCompleted}", "", MaterialTheme.colorScheme.secondary) }
                item { MetricItem("Streak", "${stats.streakDays}d", "🔥", androidx.compose.ui.graphics.Color(0xFFFF9800)) }
            }

            // Task Distribution Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Focus Details", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text("Today", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(180.dp)) {
                        val colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                        
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 22.dp.toPx()
                            var startAngle = -90f
                            stats.distribution.forEachIndexed { index, sweep ->
                                val sweepAngle = sweep * 360f
                                drawArc(
                                    color = colors[index % colors.size],
                                    startAngle = startAngle,
                                    sweepAngle = sweepAngle,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                                startAngle += sweepAngle
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${stats.focusTimeMinutes}m", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
                            Text("Total", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Legend
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        LegendItem("Work", MaterialTheme.colorScheme.primary)
                        LegendItem("Study", MaterialTheme.colorScheme.tertiary)
                        LegendItem("Other", MaterialTheme.colorScheme.secondaryContainer)
                    }
                }
            }
        }
    }
}

@Composable
fun MetricItem(title: String, value: String, sub: String, color: androidx.compose.ui.graphics.Color) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
                if (sub.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(sub, style = MaterialTheme.typography.labelSmall, color = if (sub.contains("+")) androidx.compose.ui.graphics.Color(0xFF4CAF50) else color)
                }
            }
        }
    }
}

@Composable
fun LegendItem(label: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
