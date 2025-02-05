package com.example.taskify.data

import android.content.Context
import androidx.room.*
import com.example.taskify.util.Converters

// Database class for Task entity
@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Type converters for non-primitive types
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao // Abstract function to access DAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Singleton pattern to get the database instance
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database" // Database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
