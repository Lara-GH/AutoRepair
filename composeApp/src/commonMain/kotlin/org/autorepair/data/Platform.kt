package org.autorepair.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform