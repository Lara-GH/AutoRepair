package org.autorepair

import android.app.Application
import com.google.firebase.FirebaseApp
import org.autorepair.kmp.di.initKoin

class RepairApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin()
    }

}