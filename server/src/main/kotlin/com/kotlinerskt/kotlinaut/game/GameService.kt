package com.kotlinerskt.kotlinaut.game

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.db.GameId
import com.kotlinerskt.kotlinaut.GameGrpcKt
import com.kotlinerskt.kotlinaut.RegisterClientRequest
import com.kotlinerskt.kotlinaut.RegisterClientResponse
import java.util.*

class GameService(
    private val gameDB: GameDB,
) : GameGrpcKt.GameCoroutineImplBase() {

    override suspend fun register(request: RegisterClientRequest): RegisterClientResponse {
        val clientId = request.clientId
        val token = generateToken(clientId)

        gameDB.registerOrRestart(GameId(clientId, token))

        return clientResponse(clientId, token)
    }
}

