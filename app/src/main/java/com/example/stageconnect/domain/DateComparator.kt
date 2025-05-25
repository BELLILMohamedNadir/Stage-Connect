package com.example.stageconnect.domain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateComparator(dateFormat: String) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)

    private fun compareDates(dateString1: String, dateString2: String): Int {
        val date1 = LocalDate.parse(dateString1, formatter)
        val date2 = LocalDate.parse(dateString2, formatter)
        return date1.compareTo(date2)
    }

    fun isBefore(dateString1: String, dateString2: String): Boolean {
        return compareDates(dateString1, dateString2) < 0
    }

    fun isAfter(dateString1: String, dateString2: String): Boolean {
        return compareDates(dateString1, dateString2) > 0
    }

    fun isEqual(dateString1: String, dateString2: String): Boolean {
        return compareDates(dateString1, dateString2) == 0
    }

    fun isAfterCurrentDate(dateString: String): Boolean {
        val currentDate = LocalDate.now()
        val date = LocalDate.parse(dateString, formatter)
        return date.isAfter(currentDate)
    }

}
