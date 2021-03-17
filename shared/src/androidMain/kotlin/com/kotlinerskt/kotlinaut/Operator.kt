package com.kotlinerskt.kotlinaut

import android.util.Log
import com.kotlinerskt.kotlinaut.control.*
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class Operator {
    val channel = ManagedChannelBuilder.forAddress("192.168.100.14", 8490)
        .usePlaintext()
        .build()

    private val gameStub by lazy {
        GameGrpcKt.GameCoroutineStub(channel)
    }
    private val missionStub by lazy {
        MissionControlServiceGrpcKt.MissionControlServiceCoroutineStub(channel)
    }

    private val chubyStub by lazy {
        WheresChubyServiceGrpcKt.WheresChubyServiceCoroutineStub(channel)
    }

    suspend fun register(clientId: String): RegisterClientResponse {
        val gameRequest = RegisterClientRequest
            .newBuilder()
            .setClientId(clientId)
            .build()

        return gameStub.register(gameRequest)
    }

    suspend fun startMission(clientId: String) {
        val request =
            MissionRequest.newBuilder().setPlayerInfo(PlayerInfo.getDefaultInstance()).build()

        val response: Flow<MissionResponse> = missionStub.interact(request)
        response.collect {
            Log.d("OperatorLib", "Arriving message…")
            Log.d("OperatorLib", "$it")
        }
    }

    suspend fun locateChuby(videoVersion: String): ChubyVisit {
        val request = KotlinersKTVideo.newBuilder().setVersion(videoVersion).build()

        return chubyStub.locate(request)
    }
}
