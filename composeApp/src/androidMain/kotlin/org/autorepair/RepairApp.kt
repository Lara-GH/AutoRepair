package org.autorepair

import android.app.Application
import com.google.firebase.FirebaseApp
import org.autorepair.di.contextModule
import org.autorepair.di.initKoin

class RepairApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin(
            listOf(
                contextModule(this)
            )
        )
    }
}