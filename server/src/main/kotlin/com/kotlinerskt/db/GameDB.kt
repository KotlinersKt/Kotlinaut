package com.kotlinerskt.db

typealias GameId = Pair<String, String>

val GameId.clientId: String
    get() = this.first

val GameId.clientToken: String
    get() = this.second

class GameDB {
    private val activeGames = mutableMapOf<GameId, GameStatus>()

    fun registerOrRestart(gameId: GameId) {
        activeGames[gameId] = GameStatus(
            gameId.clientId,
            gameId.clientToken,
        )
    }

    operator fun get(gameId: GameId): GameStatus =
        activeGames.computeIfAbsent(gameId) {
            GameStatus(
                gameId.clientId,
                gameId.clientToken,
            )
        }
}

