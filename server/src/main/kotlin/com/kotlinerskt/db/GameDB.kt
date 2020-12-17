package com.kotlinerskt.db

typealias GameId = Pair<String, String>

class GameDB {
    private val activeGames = mutableMapOf<GameId, GameStatus>()

    fun registerOrRestart(gameId: GameId): Nothing { //GameStatus {
        TODO("")
    }
}

