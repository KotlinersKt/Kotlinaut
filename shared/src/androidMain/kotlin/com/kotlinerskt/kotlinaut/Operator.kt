package com.kotlinerskt.kotlinaut

import android.util.Log
import com.kotlinerskt.kotlinaut.control.MissionControlServiceGrpcKt
import com.kotlinerskt.kotlinaut.control.MissionRequest
import com.kotlinerskt.kotlinaut.control.MissionResponse
import com.kotlinerskt.kotlinaut.control.PlayerInfo
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class Operator {
    val channel = ManagedChannelBuilder.forAddress("localhost", 8490)
        .usePlaintext()
        .build()

    private val stub by lazy {
        MissionControlServiceGrpcKt.MissionControlServiceCoroutineStub(channel)
    }

    suspend fun startMission() {
        val request = MissionRequest.newBuilder().setPlayerInfo(PlayerInfo.getDefaultInstance()).build()
        val response: Flow<MissionResponse> = stub.interact(request)
        response.collect {
            Log.d("OperatorLib", "Arriving message…")
            Log.d("OperatorLib", "$it")
        }
    }
}
