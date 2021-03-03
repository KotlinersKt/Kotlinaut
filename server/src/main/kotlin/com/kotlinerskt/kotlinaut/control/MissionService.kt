package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.db.GameId
import com.kotlinerskt.kotlinaut.control.MissionControlServiceGrpcKt.MissionControlServiceCoroutineImplBase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MissionService(
    private val gameDB: GameDB
) : MissionControlServiceCoroutineImplBase() {

    override fun interact(request: MissionRequest): Flow<MissionResponse> {
        val gameId = GameId(request.playerInfo.clientId, request.playerInfo.token)
        val gameStatus = gameDB[gameId]

        return flow {
            val responses = gameStatus.currentMission.contents.map(::missionWithContent)

            responses.forEach {
                delay(3000)
                emit(it)
            }
        }
    }
}
