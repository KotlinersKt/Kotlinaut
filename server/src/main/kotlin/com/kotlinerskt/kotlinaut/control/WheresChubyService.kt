package com.kotlinerskt.kotlinaut.control

class WheresChubyService : WheresChubyServiceGrpcKt.WheresChubyServiceCoroutineImplBase() {
    init {
        println("Chuby service ENABLED")
    }

    override suspend fun locate(request: KotlinersKTVideo): ChubyVisit {
        println("Localizando a Chuby… en el video ${request.version}")
        return chubyVisit(request.version)
    }

    private fun chubyVisit(version: String?): ChubyVisit {
        return ChubyVisit.newBuilder()
            .setLocation(
                when (version) {
                    "v2.0.1" -> "Chuby esta haciendo pipi en la luna"
                    "v2.0.0" -> "Chuby esta echando clavados en la quebrada"
                    else -> "Chuby esta dormido"
                }
            )
            .build()
    }
}