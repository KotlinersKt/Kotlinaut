package com.kotlinerskt.kotlinaut.login.data

import com.kotlinerskt.kotlinaut.Operator
import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureRequest
import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GRPCLoginRepository constructor(
    private val operator: Operator = Operator(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {

    override suspend fun starNewAdventure(newAdventureRequest: NewAdventureRequest): NewAdventureResponse =
        withContext(dispatcher) {
           val registerResponse = operator.register(newAdventureRequest.userId)
            NewAdventureResponse(registerResponse.clientId, registerResponse.token)
        }
}