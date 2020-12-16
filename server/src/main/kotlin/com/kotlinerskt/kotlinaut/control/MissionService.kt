package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.db.GameDB

class MissionService(
    private val gameDB: GameDB
) : ControlCenterGrpcKt.ControlCenterCoroutineImplBase() {

}

sealed class Commands {

}

data class Chapter(
    val id: String,
    val name: String,
    val missions: List<Mission>,
)

data class Mission(
    val id: String,
    val name: String,
    val contents: List<MissionResponse.GameText>,
)

data class GameStatus(
    val clientId: String,
    val clientToken: String,
    val currentChapter: Chapter,
    val currentMission: Mission,
    val commands: MutableSet<Commands>
)

val communicationBreakdown = Mission(
    "mission-1",
    "Restableciendo Comunicaciones",
    listOf(
        MissionResponse.GameText.newBuilder().setTypeValue(2).setText("Restableciendo comunicaciones").build()
    )
)
