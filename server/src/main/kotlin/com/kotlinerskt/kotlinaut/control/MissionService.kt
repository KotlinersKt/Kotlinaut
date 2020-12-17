package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB
import kotlinx.coroutines.flow.Flow

class MissionService(
    private val gameDB: GameDB
) : ControlCenterGrpcKt.ControlCenterCoroutineImplBase() {
    override fun startMission(request: MissionRequest): Flow<MissionResponse> {
        return super.startMission(request)
    }

    override suspend fun sendMessage(requests: Flow<MissionRequest>): MissionResponse {
        return super.sendMessage(requests)
    }

    override fun chatKotlinaut(requests: Flow<MissionRequest>): Flow<MissionResponse> {
        return super.chatKotlinaut(requests)
    }

    override suspend fun hello(request: MissionRequest): MissionResponse {
        return super.hello(request)
    }
}
