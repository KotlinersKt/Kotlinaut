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

fun main(args: Array<String>) {
    val kotlinautBot = bot {
        token = ""
        logLevel = LogLevel.Network.Body
        dispatch {
            command("start") {
                initAdventure(bot, message, update)
            }

            callbackQuery("newAdventure") {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(chatId = chatId, "Iniciando nueva aventura...")

            }

            callbackQuery("recoverAdventure") {
                val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
                bot.sendMessage(chatId = chatId, "Recuperando aventura, espera un momento")
            }
        }
    }
    kotlinautBot.startPolling()
}

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
