package com.kotlinerskt.kotlinaut.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ParseMode
import com.kotlinerskt.kotlinaut.GameGrpcKt
import com.kotlinerskt.kotlinaut.RegisterClientRequest
import com.kotlinerskt.kotlinaut.RegisterClientResponse
import com.kotlinerskt.kotlinaut.control.*
import com.kotlinerskt.kotlinaut.control.TextType.*
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.collect
import java.io.Closeable
import java.util.*
import java.util.concurrent.TimeUnit

class BotClient(
    private val channel: ManagedChannel
) : Closeable {
    private val gameStub by lazy { GameGrpcKt.GameCoroutineStub(channel) }
    private val missionStub by lazy { MissionControlServiceGrpcKt.MissionControlServiceCoroutineStub(channel) }

    lateinit var registerInfo: RegisterClientResponse

    suspend fun register() {
        println("StartRegister")
        val request = RegisterClientRequest
            .newBuilder()
            .setClientId("Gorro${Calendar.getInstance().time}").build()
        registerInfo = gameStub.register(request)
        println(registerInfo.clientId)
        println(registerInfo.token)

    }

    suspend fun interact(bot: Bot, chatId: Long) {
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
            val textFormated = formatTextToHtml(it.content)
            bot.sendMessage(
                chatId = chatId,
                text = textFormated,
                parseMode = ParseMode.HTML
            )
        }
    }

    private fun formatTextToHtml(textGame: MissionResponse.GameText) = when (textGame.type) {
        NARRATOR -> "<code>${textGame.text}</code>"
        CHAPTER -> "<u>${textGame.text}</u>"
        MISSION -> "<u><i>${textGame.text}</i></u>"
        KOTLINAUT -> "<i>${textGame.text}</i>"
        BOSS -> "<b>${textGame.text}</b>"
        PLAIN -> textGame.text
        ERROR -> "<pre>${textGame.text}</pre>"
        UNRECOGNIZED -> textGame.text
        else -> textGame.text
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

//fun main() {
//    println("Starting communication...")
//
//    runBlocking {
//        val client = BotClient(ManagedChannelBuilder.forAddress("localhost", 8490).usePlaintext().build())
//        client.register()
//        delay(2000)
//        client.interact()
//    }
//}
