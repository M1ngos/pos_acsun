package com.acsun.pos

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform