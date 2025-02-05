package com.example.taskify.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskify.data.Task
import com.example.taskify.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(navController: NavHostController, viewModel: TaskViewModel, taskId: Int = 0) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(Date()) }
    var priorityColor by remember { mutableIntStateOf(0) } // Stores priority color as Int
    var titleError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(taskId) {
        if (taskId != 0) {
            scope.launch {
                val task = viewModel.getTaskById(taskId)
                task?.let {
                    title = it.title
                    description = it.description
                    dueDate = it.dueDate
                    priorityColor = it.priorityColor // Map stored color when editing
                }
            }
        } else {
            title = ""
            description = ""
            dueDate = Date()
            priorityColor = 0
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            dueDate = calendar.time
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (taskId == 0) "Add Task" else "Edit Task") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = false // Clear error when typing
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = titleError,
                    supportingText = { if (titleError) Text("Title cannot be empty") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = false // Clear error when typing
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = descriptionError,
                    supportingText = { if (descriptionError) Text("Description cannot be empty") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { datePickerDialog.show() }) {
                        Text(text = "Select Due Date")
                    }

                    // Show color picker
                    ColorPicker(
                        selectedColor = priorityColor,
                        onColorSelected = { color ->
                            priorityColor = color // Set priorityColor
                        }
                    )
                }

                Button(onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    }
                    if (description.isBlank()) {
                        descriptionError = true
                    }
                    if (title.isNotBlank() && description.isNotBlank()) {
                        val newTask = Task(
                            id = if (taskId != 0) taskId else 0,
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            priorityColor = priorityColor, // Store the selected color
                            isCompleted = false
                        )
                        viewModel.addTask(newTask)
                        navController.popBackStack()
                    }
                }) {
                    Text(if (taskId == 0) "Save Task" else "Update Task")
                }
            }
        }
    )
}

@Composable
fun ColorPicker(selectedColor: Int, onColorSelected: (Int) -> Unit) {
    val colors = listOf(
        Color.Green,
        Color.Yellow,
        Color.Red
    )
    Row {
        colors.forEach { color ->
            val isSelected = selectedColor == color.toArgb() // Convert color to Int and compare
            IconButton(
                onClick = {
                    onColorSelected(color.toArgb()) // Convert color to Int when selected
                },
                modifier = Modifier
                    .padding(4.dp)
                    .background(color, CircleShape)
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                    contentDescription = "Color",
                    tint = if (isSelected) Color.Black else Color.White
                )
            }
        }
    }
}
