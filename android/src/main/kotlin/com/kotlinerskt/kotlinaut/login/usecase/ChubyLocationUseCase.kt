package com.kotlinerskt.kotlinaut.login.usecase

import com.kotlinerskt.kotlinaut.login.data.LocateRepository
import com.kotlinerskt.kotlinaut.login.data.models.ChubyVisitResponse
import com.kotlinerskt.kotlinaut.login.data.models.KotlinersVideoRequest
import timber.log.Timber

class ChubyLocationUseCase constructor(
    private val locateRepository: LocateRepository,
) {

    suspend operator fun invoke(version: String): ChubyVisitResponse {
        val response = locateRepository.locateChuby(version.toKotlinersVideoRequest())
        Timber.i("Response from server: $response")
        return response
    }

    private fun String.toKotlinersVideoRequest() = KotlinersVideoRequest(version = this)

}