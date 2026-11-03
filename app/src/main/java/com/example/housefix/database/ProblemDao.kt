package com.example.housefix.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.housefix.model.Problem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemDao {

    @Query("SELECT * FROM problems")
    fun getAllData(): Flow<List<Problem>>

    @Query("SELECT * FROM problems where id=(:id)")
    suspend fun getData(id:Int): Problem

    @Update
    suspend fun updateData(problem: Problem)
}