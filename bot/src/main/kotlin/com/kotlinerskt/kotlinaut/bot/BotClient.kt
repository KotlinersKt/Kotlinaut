package com.kotlinerskt.kotlinaut.bot

import com.kotlinerskt.kotlinaut.GameGrpcKt
import com.kotlinerskt.kotlinaut.RegisterClientRequest
import com.kotlinerskt.kotlinaut.RegisterClientResponse
import com.kotlinerskt.kotlinaut.control.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import java.util.concurrent.TimeUnit

class BotClient(
    private val channel: ManagedChannel
) : Closeable {
    private val gameStub by lazy { GameGrpcKt.GameCoroutineStub(channel) }
    private val missionStub by lazy { MissionControlServiceGrpcKt.MissionControlServiceCoroutineStub(channel) }

    lateinit var registerInfo: RegisterClientResponse

    suspend fun register() {
        registerInfo = gameStub.register(RegisterClientRequest.newBuilder().setClientId("Sier").build())
        println(registerInfo.clientId)
        println(registerInfo.token)

    }

    suspend fun interact() {
        val dataFlow = missionStub.interact(
            MissionRequest.newBuilder()
                .setPlayerInfo(
                    PlayerInfo.newBuilder().setClientId(registerInfo.clientId).setToken(registerInfo.token).build()
                )
                .setInteraction(
                    Interaction.newBuilder().setType(InteractionType.MESSAGE).setValue("/start-alv").build()
                )
                .build()
        )

        dataFlow.collect {
            println("Mission information arrived...")
            println(it)
        }
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

fun main() {
    println("Starting communication...")

    runBlocking {
        val client = BotClient(ManagedChannelBuilder.forAddress("localhost", 8490).usePlaintext().build())
        client.register()
        delay(2000)
        client.interact()
    }
}
