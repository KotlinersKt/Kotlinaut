package com.kotlinerskt.kotlinaut

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.logging.LogLevel
import com.kotlinerskt.kotlinaut.bot.BotClient
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    println("Starting communication")

    runBlocking {
        val client = BotClient(ManagedChannelBuilder.forAddress("localhost", 8490).usePlaintext().build())
        val kotlinautBot = bot {
            token = "1495783449:AAE_VOgZKtlVMpifAqJDECGNTgSOV6aIXrc"
            logLevel = LogLevel.Network.Body
            dispatch {

                command("start") {
                    initAdventure(bot, message, update)
                }

                command("manuals") {
                    message.chat.id.let { id ->
                        bot.sendMessage(
                            chatId = id,
                            text = "Comandos disponibles" +
                                    "\n/manuals" +
                                    "\n/tablero",
                        )
                    }
                }

                command("tablero") {
                    message.chat.id.let { id ->
                        println("send photo")
                        try {
                            val photo =
                                this::class.java.classLoader.getResource("images/mission_one.png")?.file
                            println("----> $photo")
                            bot.sendPhoto(
                                chatId = id,
                                photo = photo!!
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                command("chuby") {
                    val video = args.firstOrNull()
                    println("ARGGSSS ->>> ${args.joinToString()}")
                    println("$video")
                    runBlocking {
                        val location = client.whereIsChuby(message.removeCommand("chuby"))
                        println("Chuby Location---->>>>> $location")
                        bot.sendMessage(chatId = message.chat.id, text = location)
                    }
                }

                command("playlist") {
                    message.chat.id.let { id ->
                        bot.sendMessage(
                            chatId = id,
                            text = "https://open.spotify.com/playlist/3k6P7cEIFJugvnbZWbODUc?si=SIWhrTYFQt6JMFGPIViLSA",
                        )
                    }
                }

                callbackQuery("newAdventure") {
                    val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                    bot.sendMessage(chatId = chatId, "Iniciando nueva aventura...")
                    runBlocking {
                        client.register()
                        client.interact(bot, chatId)
                    }
                }

                callbackQuery("recoverAdventure") {
                    val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                    bot.sendMessage(chatId = chatId, "Recuperando aventura, espera un momento")
                }
            }
        }
        kotlinautBot.startPolling()
    }
}

fun Message.removeCommand(command: String): String = text?.removePrefix("/$command")?.trim() ?: ""

fun initAdventure(bot: Bot, message: Message, update: Update) {
    message.chat.id.let { id ->
        bot.sendMessage(id, "Bienvenido a El Kotlinaut la aventura")
        val inlineKeyboardMarkup = InlineKeyboardMarkup.create(
            listOf(InlineKeyboardButton.CallbackData(text = "Nueva aventura", callbackData = "newAdventure")),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Iniciar donde me quede",
                    callbackData = "recoverAdventure"
                )
            )
        )
        bot.sendMessage(
            chatId = message.chat.id,
            text = "Antes de iniciar, tienes alguna aventura anterior?",
            replyMarkup = inlineKeyboardMarkup
        )
    }

}
