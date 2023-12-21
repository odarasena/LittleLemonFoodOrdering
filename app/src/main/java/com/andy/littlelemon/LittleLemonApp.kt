package com.andy.littlelemon

import android.app.Application
import com.andy.littlelemon.data.local.LittleLemonDatabase

class LittleLemonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        LittleLemonDatabase.buildDatabase(this)
    }
}