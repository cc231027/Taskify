package com.example.taskify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify.data.TaskDatabase
import com.example.taskify.ui.Navigation
import com.example.taskify.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the TaskDao from the TaskDatabase instance
        val dao = TaskDatabase.getDatabase(this).taskDao()

        // Initialize the ViewModel with the TaskDao
        val viewModel = TaskViewModel(dao)

        // Set the content view with the Navigation composable, passing the ViewModel
        setContent {
            Navigation(viewModel = viewModel)
        }
    }
}
