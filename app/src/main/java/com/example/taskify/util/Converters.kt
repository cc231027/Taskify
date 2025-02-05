package com.example.taskify.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    // Converts a timestamp (Long) to a Date object
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // Converts a Date object to a timestamp (Long)
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
