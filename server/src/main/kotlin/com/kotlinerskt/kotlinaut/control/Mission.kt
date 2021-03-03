package com.kotlinerskt.kotlinaut.control

fun missionWithContent(content: MissionResponse.GameText): MissionResponse = MissionResponse.newBuilder()
    .setContent(content)
    .build()
