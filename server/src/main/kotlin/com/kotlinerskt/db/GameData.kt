package com.kotlinerskt.db

import com.kotlinerskt.kotlinaut.control.MissionResponse

data class CommandOrAction(val name: String, val parameters: List<String>)

data class Chapter(
    val id: String,
    val name: String,
    val missions: List<Mission>,
)

data class Mission(
    val id: String,
    val name: String,
    val contents: List<MissionResponse.GameText> = emptyList(),
)

data class GameStatus(
    val clientId: String,
    val clientToken: String,
    val currentChapter: Chapter,
    val currentMission: Mission,
    val features: MutableSet<CommandOrAction>
)

val communicationBreakdown = Mission(
    "mission-1",
    "Restableciendo Comunicaciones",
    listOf(
        MissionResponse.GameText.newBuilder().setTypeValue(2).setText("Restableciendo comunicaciones").build()
    )
)
