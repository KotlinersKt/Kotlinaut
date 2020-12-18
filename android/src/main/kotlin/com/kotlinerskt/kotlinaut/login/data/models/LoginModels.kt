package com.kotlinerskt.kotlinaut.login.data.models

data class NewAdventureRequest(
    val userId: String
)

data class NewAdventureResponse(
    val userToken: String
)