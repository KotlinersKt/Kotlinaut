package com.kotlinerskt.kotlinaut.game

import java.util.*

fun generateToken(id: String): String = "$id:${UUID.randomUUID()}".reversed()
