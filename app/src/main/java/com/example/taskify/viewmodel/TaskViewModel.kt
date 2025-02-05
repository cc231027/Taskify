package com.example.taskify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskify.data.Task
import com.example.taskify.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    // MutableStateFlow to hold the list of tasks
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    // Exposed immutable StateFlow to observe tasks
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        // Collect tasks from the database on initialization
        viewModelScope.launch {
            taskDao.getAllTasks().collect { _tasks.value = it }
        }
    }

    // Function to add a new task or update an existing one
    fun addTask(task: Task) = viewModelScope.launch {
        if (task.id == 0) { // New task
            taskDao.insertTask(task)
        } else { // Update existing task
            taskDao.updateTask(task)
        }
    }

    // Function to delete a task
    fun deleteTask(task: Task) = viewModelScope.launch { taskDao.deleteTask(task) }

    // Suspend function to get a task by its ID
    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)

    // Function to update task completion status
    fun updateTaskCompleted(task: Task) = viewModelScope.launch { taskDao.updateTask(task) }
}
