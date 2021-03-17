package com.kotlinerskt.kotlinaut.login.data

import com.kotlinerskt.kotlinaut.login.data.models.ChubyVisitResponse
import com.kotlinerskt.kotlinaut.login.data.models.KotlinersVideoRequest

interface LocateRepository {

    suspend fun locateChuby(kotlinersVideoRequest: KotlinersVideoRequest): ChubyVisitResponse
}