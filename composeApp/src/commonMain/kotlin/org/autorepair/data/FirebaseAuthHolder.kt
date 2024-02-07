package org.autorepair.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

object FirebaseAuthHolder {
    val auth = Firebase.auth
}