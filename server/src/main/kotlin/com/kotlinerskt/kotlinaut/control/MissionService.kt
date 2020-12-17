package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.db.GameId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

class MissionService(
    private val gameDB: GameDB
) : MissionControlServiceGrpcKt.MissionControlServiceCoroutineImplBase() {

    override fun interact(request: MissionRequest): Flow<MissionResponse> {
        val gameId = GameId(request.playerInfo.clientId, request.playerInfo.token)
        val gameStatus = gameDB[gameId]

        return gameStatus.currentMission.contents.asFlow()
            .map {
                delay(3000)
                MissionResponse.newBuilder()
                    .setContent(it)
                    .build()
            }
            .onCompletion {
                gameStatus.
            }

//        return flow {
//            val responses = gameStatus.currentMission.contents.map {
//                MissionResponse.newBuilder()
//                    .setContent(it)
//                    .build()
//            }
//
//            responses.forEach {
//                emit(it)
//                delay(3000)
//            }
//        }
    }
}
