package com.kotlinerskt.kotlinaut.login.usecase

import com.kotlinerskt.kotlinaut.login.data.LoginRepository
import com.kotlinerskt.kotlinaut.login.data.models.NewAdventureRequest
import timber.log.Timber

class NewAdventureUseCase constructor(
    private val loginRepository: LoginRepository,
) {

    suspend operator fun invoke(userId: String): String {
        val response = loginRepository.starNewAdventure(userId.toNewAdventureRequest())
        Timber.i("Response from server: $response")
        return response.userToken
    }

    private fun String.toNewAdventureRequest() = NewAdventureRequest(userId = this)

}