package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.db.GameId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class MissionService(
    private val gameDB: GameDB
) : MissionControlServiceGrpcKt.MissionControlServiceCoroutineImplBase() {

    override fun interact(request: MissionRequest): Flow<MissionResponse> {
        val gameId = GameId(request.playerInfo.clientId, request.playerInfo.token)
        val gameStatus = gameDB[gameId]


        return flow {
            val responses = gameStatus.currentMission.contents.map {
                MissionResponse.newBuilder()
                    .setContent(it)
                    .build()
            }

            responses.forEach {
                delay(3000)
                emit(it)
            }


        }
    }
}
