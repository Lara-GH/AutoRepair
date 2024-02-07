package org.autorepair

import android.app.Application
import com.google.firebase.FirebaseApp

class RepairApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}