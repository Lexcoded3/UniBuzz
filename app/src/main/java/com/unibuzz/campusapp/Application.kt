package com.unibuzz.campusapp

import android.app.Application
import com.unibuzz.campusapp.data.local.AppDatabase

class Application : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this)
    }
}
