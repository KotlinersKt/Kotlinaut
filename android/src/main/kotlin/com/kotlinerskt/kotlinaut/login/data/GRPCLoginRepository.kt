package com.kotlinerskt.kotlinaut.login.data

import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureRequest
import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GRPCLoginRepository constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {

    override suspend fun starNewAdventure(newAdventureRequest: NewAdventureRequest): NewAdventureResponse =
        withContext(dispatcher) {
            // TODO: 17/12/20 Dummy implementation 
            NewAdventureResponse(newAdventureRequest.userId, newAdventureRequest.userId.reversed())
        }
}