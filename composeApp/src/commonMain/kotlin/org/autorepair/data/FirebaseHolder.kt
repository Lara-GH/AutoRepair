package org.autorepair.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.database

object FirebaseHolder {
    val auth = Firebase.auth
    val database = Firebase.database
}