package com.panyou.md3todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.m3.Markdown
import com.panyou.md3todo.domain.model.Task

@Composable
fun TaskCard(task: Task, onClick: () -> Unit, onCheckChanged: (Boolean) -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(24.dp), // MD3 Expressive large rounded corners
        colors = CardDefaults.cardColors(
            containerColor = if (task.isImportant) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = onCheckChanged
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface
                )
            }
            if (task.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Markdown(
                    content = task.description,
                    modifier = Modifier.padding(start = 48.dp) // Indent to align with text
                )
            }
        }
    }
}