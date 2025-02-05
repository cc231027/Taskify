package com.example.taskify.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.taskify.data.Task
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun TaskItem(
    task: Task,
    onDelete: (Task) -> Unit,
    navController: NavHostController,
    onTaskCompletedChange: (Task) -> Unit
) {
    // Format creation and due dates for display
    val creationDate = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(task.createdDate)
    val dueDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(task.dueDate)
    val scope = rememberCoroutineScope()
    val fabColor = MaterialTheme.colorScheme.secondary
    val maxWords = 15
    val description = task.description.split(" ").take(maxWords).joinToString(" ") + if (task.description.split(" ").size > maxWords) "..." else ""

    // Convert priorityColor (Int) to a Color object for use in the UI
    val priorityColor = Color(task.priorityColor)

    // State to control the visibility of the deletion confirmation dialog
    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    // Dialog to confirm task deletion
    if (showDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this task?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskToDelete?.let {
                            onDelete(it)  // Call the delete function
                        }
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    // Card that wraps the task item, clickable to navigate to task detail
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("taskDetail/${task.id}") },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, fabColor)
    ) {
        // List item layout with task details
        ListItem(
            headlineContent = {
                Text(
                    text = task.title,  // Task title
                    color = Color(0xFF6650a4),
                    fontWeight = FontWeight.Medium
                )
            },
            supportingContent = {
                Column {
                    // Display task description with a limit of two lines
                    Text(
                        text = description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Show task creation and due dates
                    Text(
                        text = "Created: $creationDate",
                        color = Color.LightGray
                    )
                    Text(
                        text = "Due: $dueDate",
                        color = Color.Gray
                    )

                    // Divider using task priority color to separate content
                    Divider(
                        modifier = Modifier.padding(top = 8.dp),
                        color = priorityColor, // Use the converted priority color
                        thickness = 5.dp
                    )
                }
            },
            trailingContent = {
                Row {
                    // Checkbox to mark the task as completed
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = {
                            val updatedTask = task.copy(isCompleted = it)
                            onTaskCompletedChange(updatedTask) // Update task completion status
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                    // Delete button to trigger the deletion confirmation dialog
                    IconButton(onClick = {
                        taskToDelete = task
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        )
    }
}
