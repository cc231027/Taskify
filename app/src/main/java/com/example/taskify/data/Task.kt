package com.example.taskify.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Task entity representing a task in the database
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val createdDate: Date = Date(),
    val dueDate: Date,
    val isCompleted: Boolean = false,
    val priorityColor: Int
)
