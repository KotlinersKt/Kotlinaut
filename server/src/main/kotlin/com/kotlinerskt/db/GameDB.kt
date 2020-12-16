package com.kotlinerskt.db

import com.kotlinerskt.kotlinaut.game.GameStatus

typealias GameId = Pair<String, String>

class GameDB {
    private val activeGames = mutableMapOf<GameId, GameStatus>()

    fun registerOrRestart(gameId: GameId): Nothing { //GameStatus {
        TODO("")
    }
}

