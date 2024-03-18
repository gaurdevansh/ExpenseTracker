package com.example.expensetracker.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

object DateUtils {

    fun getDateInFormat(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH))
    }

    fun getDateInFormat2(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("d MMM, YY", Locale.ENGLISH))
    }

    fun getStartPeriod(currentDate: LocalDate, timeFrame: TimeFrame): LocalDate {
        return when(timeFrame) {
            TimeFrame.WEEK -> {
                currentDate.with(DayOfWeek.MONDAY)
            }
            TimeFrame.MONTH -> {
                currentDate.withDayOfMonth(1)
            }
            TimeFrame.YEAR -> {
                currentDate.withYear(1)
            }
        }
    }

    fun getEndPeriod(currentDate: LocalDate, timeFrame: TimeFrame): LocalDate {
        return when(timeFrame) {
            TimeFrame.WEEK -> {
                currentDate.plusDays(6)
            }
            TimeFrame.MONTH -> {
                currentDate.with(TemporalAdjusters.lastDayOfMonth())

            }
            TimeFrame.YEAR -> {
                currentDate.with(TemporalAdjusters.lastDayOfYear())
            }
        }
    }
}