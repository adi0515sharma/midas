package com.example.midas

import android.app.Application
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.room.UserInfoDatabase

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefferenceStorage.getInstance(this)
        UserInfoDatabase.Companion.getDatabase(this)

    }
}