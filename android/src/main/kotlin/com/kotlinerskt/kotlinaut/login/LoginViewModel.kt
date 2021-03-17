package com.kotlinerskt.kotlinaut.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kotlinerskt.kotlinaut.login.data.models.ChubyVisitResponse
import com.kotlinerskt.kotlinaut.login.usecase.ChubyLocationUseCase
import com.kotlinerskt.kotlinaut.login.usecase.NewAdventureUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel constructor(
    private val newAdventureUseCase: NewAdventureUseCase,
    private val chubyLocationUseCase: ChubyLocationUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Idle)
    val state: StateFlow<State> get() = _state

    private val _chubyLocation: MutableStateFlow<State> = MutableStateFlow(State.Idle)
    val chubyLocation: Flow<State> get() = _chubyLocation

    fun logInUser(userId: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            runCatching {
                newAdventureUseCase(userId)
            }.onSuccess {
                _state.value = State.StartAdventure
                _state.value = State.Idle
            }.onFailure {
                Timber.e(it, "Yep we screwed it up")
                _state.value = State.Error(it.message!!)
                _state.value = State.Idle
            }
        }
    }

    fun locateChuby(version: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            runCatching {
                chubyLocationUseCase(version)
            }.onSuccess {
                _state.value = State.ChubyLocation(it)
                _state.value = State.Idle
            }.onFailure {
                Timber.e(it, "Yep we screwed it up")
                _state.value = State.Error(it.message!!)
                _state.value = State.Idle
            }
        }
    }


    sealed class State {
        object Idle : State()
        object Loading : State()
        object StartAdventure : State()
        data class ChubyLocation(val chubyVisit: ChubyVisitResponse) : State()
        data class Error(val errorMessage: String) : State()
    }

}

class LoginViewModelFactory(
    private val newAdventureUseCase: NewAdventureUseCase,
    private val chubyLocationUseCase: ChubyLocationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NewAdventureUseCase::class.java)
            .newInstance(newAdventureUseCase, chubyLocationUseCase)
    }
}
