package com.example.housefix.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.housefix.database.ProblemDatabase
import com.example.housefix.model.Problem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "housefix.db"
class ProblemRepository
private constructor(context: Context ,
                    private val coroutineScope: CoroutineScope = GlobalScope) {

    private val database: ProblemDatabase = Room.databaseBuilder(
        context.applicationContext,
        ProblemDatabase::class.java,
        DATABASE_NAME
    ).createFromAsset(DATABASE_NAME).build()

    fun getAllData(): Flow<List<Problem>> = database.problemDao().getAllData()

    suspend fun getData(id:Int): Problem = database.problemDao().getData(id)

     fun updateData(problem: Problem) {
        coroutineScope.launch {
            database.problemDao().updateData(problem)
        }
    }

    companion object {
        private var INSTANCE: ProblemRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ProblemRepository(context)
            }
        }

        fun get(): ProblemRepository {
            return INSTANCE?: throw IllegalStateException("Problem repository must be initialized")
        }
    }

}