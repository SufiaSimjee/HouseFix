package com.example.housefix.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.housefix.model.Problem

@Database(entities = [Problem::class] , version = 1)
@TypeConverters(ProblemTypeConvetors::class)

abstract class ProblemDatabase: RoomDatabase() {
    abstract fun problemDao(): ProblemDao
}