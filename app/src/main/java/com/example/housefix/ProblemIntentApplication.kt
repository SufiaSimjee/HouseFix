package com.example.housefix

import android.app.Application
import com.example.housefix.repository.ProblemRepository

class ProblemIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ProblemRepository.initialize(this)
    }
}