package com.example.expensetracker.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    fun getDateInFormat(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH))
    }

    fun getDateInFormat2(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("d MMM, YY", Locale.ENGLISH))
    }
}