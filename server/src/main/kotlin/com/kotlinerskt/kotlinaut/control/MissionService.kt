package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB
import kotlinx.coroutines.flow.Flow

class MissionService(
    private val gameDB: GameDB
) : MissionControlServiceGrpcKt.MissionControlServiceCoroutineImplBase() {

    override fun interact(request: MissionRequest): Flow<MissionResponse> {
        return super.interact(request)
    }
}
