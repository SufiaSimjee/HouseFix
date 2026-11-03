package com.example.housefix.database

import androidx.room.TypeConverter
import java.util.Date

class ProblemTypeConvetors {

    @TypeConverter
    fun fromDate(date: Date): Long{
        return date.time
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}