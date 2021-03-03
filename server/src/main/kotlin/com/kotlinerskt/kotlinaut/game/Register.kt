package com.kotlinerskt.kotlinaut.game

import com.kotlinerskt.kotlinaut.RegisterClientResponse

fun clientResponse(clientId: String, token: String): RegisterClientResponse = RegisterClientResponse
    .newBuilder()
    .setClientId(clientId)
    .setToken(token)
    .build()
