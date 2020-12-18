package com.kotlinerskt.kotlinaut.login.data

import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureRequest
import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureResponse

interface LoginRepository {

    suspend fun starNewAdventure(newAdventureRequest: NewAdventureRequest): NewAdventureResponse
}
