package com.example.housefix.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "problems")
data class Problem(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title: String,
    val description:String,
    val isSolved: Boolean,
    val date: Date
)
