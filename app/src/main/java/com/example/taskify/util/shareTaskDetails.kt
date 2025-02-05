package com.example.taskify.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.net.Uri
import com.example.taskify.data.Task
import java.text.SimpleDateFormat
import java.util.*

fun shareTaskDetails(context: Context, task: Task) {
    // Format the task details into a text string
    val taskDetails = """
        Task Title: ${task.title}
        Description: ${task.description}
        Created: ${SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(task.createdDate)}
        Due: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(task.dueDate)}
    """.trimIndent()

    // Create an Intent to share the task details
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, taskDetails)
        type = "text/plain"
    }

    // Launch the share intent (this opens the system share dialog)
    context.startActivity(Intent.createChooser(shareIntent, "Share task details via"))
}
