package com.example.expensetracker.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate): String {
        return date.format(formatter)
    }
}