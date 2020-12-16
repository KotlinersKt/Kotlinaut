package com.kotlinerskt.kotlinaut

import com.kotlinerskt.db.GameDB
import com.kotlinerskt.kotlinaut.game.GameService
import io.grpc.ServerBuilder

class KotlinautGameServer(
    private val port: Int,
) {
    private val gameDB = GameDB()

    private val server = ServerBuilder.forPort(port)
        .addService(GameService(gameDB))
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@KotlinautGameServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}

fun main() {
    val port = 8490
    val server = KotlinautGameServer(port)
    server.start()
    server.blockUntilShutdown()
}
