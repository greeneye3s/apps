package com.example.greeneye

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform