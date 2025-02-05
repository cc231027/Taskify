package com.example.taskify.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.taskify.data.Task
import com.example.taskify.util.shareTaskDetails
import com.example.taskify.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavHostController, viewModel: TaskViewModel, taskId: Int) {
    var task by remember { mutableStateOf<Task?>(null) }
    val scope = rememberCoroutineScope()

    // Load the task by taskId when the screen is launched
    LaunchedEffect(taskId) {
        scope.launch {
            task = viewModel.getTaskById(taskId)  // Fetch task by ID from the ViewModel
        }
    }

    Scaffold(
        topBar = {
            // Header card with task details title and share button
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6650a4))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Task Details",  // Title of the screen
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    // Share Button (visible when task is available)
                    task?.let {
                        ShareButton(task = it)
                    }
                }
            }
        },
        content = { padding ->
            task?.let {
                // Format the task's created and due date
                val creationDate: String = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(it.createdDate)
                val dueDate: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(it.dueDate)

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    // Display task title
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display task description
                    Text(text = it.description)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display task creation date
                    Text(
                        text = "Created: $creationDate",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Display task due date
                    Text(
                        text = "Due: $dueDate",
                        color = Color.Gray
                    )

                    // Display task priority color as a colored box
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(it.priorityColor))
                    )
                }
            }
        },
        floatingActionButton = {
            task?.let {
                // Edit button to navigate to task form screen for editing
                FloatingActionButton(
                    onClick = {
                        navController.navigate("taskForm/${it.id}") // Navigate to task form screen with task ID
                    },
                    containerColor = Color(0xFF6650a4),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Task",
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun ShareButton(task: Task) {
    val context = LocalContext.current

    // Share task details on button click
    IconButton(onClick = {
        shareTaskDetails(context, task)  // Share task details using helper function
    }) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "Share Task",
            tint = Color.White
        )
    }
}
