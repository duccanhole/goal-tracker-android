package com.example.myapplication.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtils {
    fun toISOString(hours: Int, minutes: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun toDate(dateString: String, format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())

        return dateFormat.parse(dateString)
    }

    fun toCalendar(dateString: String, format: String = "yyyy-MM-dd HH:mm:ss"): Calendar? {
        if(dateString.isBlank() || dateString.isEmpty()) return null
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(dateString)

        return calendar
    }

    fun formatDate(dateString: String, from: String = "yyyy-MM-dd HH:mm:ss", to: String = "HH:mm"): String {
        if(dateString.isEmpty()) return ""

        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Parse input date string into Date object
        val inputDate = inputDateFormat.parse(dateString)

        // Format Date object into output date string
        return outputDateFormat.format(inputDate)
    }

}