package com.example.expensetracker.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.format(formatter)
    }
}