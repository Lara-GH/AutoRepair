package org.autorepair

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.permission.permissionUtil
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)
        setContent {
            App()
        }

        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}