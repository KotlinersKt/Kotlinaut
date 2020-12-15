package com.kotlinerskt.kotlinaut.control

import com.kotlinerskt.kotlinaut.GameGrpcKt
import com.kotlinerskt.kotlinaut.RegisterClientRequest
import com.kotlinerskt.kotlinaut.RegisterClientResponse
import io.grpc.ServerBuilder

class KotlinautGameServer(
    val port: Int,
) {
    val server = ServerBuilder.forPort(port).addService(GameServer()).build()

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

class GameServer : GameGrpcKt.GameCoroutineImplBase() {
    override suspend fun register(request: RegisterClientRequest): RegisterClientResponse {
        val clientId = request.clientId
        val token = generateToken(clientId)

        return RegisterClientResponse
            .newBuilder()
            .setClientId(clientId)
            .setToken(token)
            .build()
    }
}

fun generateToken(id: String): String = id.reversed()


fun main() {
    val port = 8490
    val server = KotlinautGameServer(port)
    server.start()
    server.blockUntilShutdown()
}
