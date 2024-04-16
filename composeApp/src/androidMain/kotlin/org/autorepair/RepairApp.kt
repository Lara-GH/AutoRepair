package org.autorepair

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.autorepair.di.contextModule
import org.autorepair.di.initKoin

class RepairApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground,
                showPushNotification = true,
                notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData()
            )
        )
        initKoin(
            listOf(
                contextModule(this)
            )
        )
    }
}