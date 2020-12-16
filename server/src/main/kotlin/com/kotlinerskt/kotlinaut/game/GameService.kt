package com.kotlinerskt.kotlinaut.game

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.kotlinaut.GameGrpcKt
import com.kotlinerskt.kotlinaut.RegisterClientRequest
import com.kotlinerskt.kotlinaut.RegisterClientResponse

class GameService(
    private val gameDB: GameDB,
) : GameGrpcKt.GameCoroutineImplBase() {
    override suspend fun register(request: RegisterClientRequest): RegisterClientResponse {
        val clientId = request.clientId
        val token = generateToken(clientId)

        return RegisterClientResponse
            .newBuilder()
            .setClientId(clientId)
            .setToken(token)
            .build()
    }
}

fun generateToken(id: String): String = id.reversed()

