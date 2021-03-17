package com.kotlinerskt.kotlinaut.login.data

import com.kotlinerskt.kotlinaut.Operator
import com.kotlinerskt.kotlinaut.login.data.models.ChubyVisitResponse
import com.kotlinerskt.kotlinaut.login.data.models.KotlinersVideoRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GRPCLocateRepository constructor(
    private val operator: Operator = Operator(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocateRepository {

    override suspend fun locateChuby(kotlinersVideoRequest: KotlinersVideoRequest): ChubyVisitResponse =
        withContext(dispatcher) {
            val chubyVisitResponse = operator.locateChuby(kotlinersVideoRequest.version)
            ChubyVisitResponse(chubyVisitResponse.location)
        }
}