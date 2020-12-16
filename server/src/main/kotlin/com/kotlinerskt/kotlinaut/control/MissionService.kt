package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB

class MissionService(
    private val gameDB: GameDB
) : ControlCenterGrpcKt.ControlCenterCoroutineImplBase() {

}
