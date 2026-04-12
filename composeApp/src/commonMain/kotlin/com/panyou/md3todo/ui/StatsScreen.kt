package com.panyou.md3todo.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptApi(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Retrospective", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Text("Weekly Productivity", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))
            
            // A simple faux-chart mimicking Tomato's visual style (MD3 colors + rounded bars)
            val barColor = MaterialTheme.colorScheme.primary
            val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
            
            Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barWidth = size.width / 14f
                    val maxTasks = 10f
                    val mockData = listOf(3f, 5f, 2f, 8f, 4f, 9f, 6f)
                    
                    mockData.forEachIndexed { index, value ->
                        val x = index * (barWidth * 2) + barWidth / 2
                        
                        // Background track
                        drawRoundRect(
                            color = surfaceColor,
                            topLeft = Offset(x, 0f),
                            size = Size(barWidth, size.height),
                            cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                        )
                        
                        // Foreground value
                        val height = (value / maxTasks) * size.height
                        drawRoundRect(
                            color = barColor,
                            topLeft = Offset(x, size.height - height),
                            size = Size(barWidth, height),
                            cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("AI Insights", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("You've been highly productive on Thursdays. Consider scheduling your most complex tasks then! (Simulated RikkaHub/Fluxdo AI summary)")
                }
            }
        }
    }
}