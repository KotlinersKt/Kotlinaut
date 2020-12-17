package com.kotlinerskt.db

import com.kotlinerskt.kotlinaut.control.MissionResponse
import com.kotlinerskt.kotlinaut.control.TextType

data class CommandOrAction(val name: String, val parameters: List<String>)

data class Chapter(
    val id: String,
    val name: String,
    val missions: List<Mission>,
)

data class Mission(
    val id: String,
    val name: String,
    val contents: List<MissionResponse.GameText> = emptyList(),
)

data class GameStatus(
    val clientId: String,
    val clientToken: String,
    val currentChapter: Chapter = chapterOne(),
    val currentMission: Mission = currentChapter.missions.first(),
    val features: MutableSet<CommandOrAction> = mutableSetOf()
)

private fun chapterOne(): Chapter = Chapter(
    "chapter-1",
    "Capitulo 1",
    listOf(
        Mission(
            "m-1-0",
            "Capitulo 1",
            listOf(
                MissionResponse.GameText.newBuilder().setType(TextType.CHAPTER).setText("Capitulo 1").build(),
                MissionResponse.GameText.newBuilder().setType(TextType.NARRATOR)
                    .setText("Initial Kotlinaut dialog").build(),
                MissionResponse.GameText.newBuilder().setType(TextType.KOTLINAUT)
                    .setText(
                        """
                        …
                        …
                        Hola?! 
                        Hay alguien ahí?!, Comando…conteste…
                        …
                        …
                        Necesito Ayuda.
                    """.trimIndent()
                    ).build()
            ),
        ),
        Mission(
            "m-1-1",
            "Restableciendo comunicaciones",
            listOf(
                MissionResponse.GameText.newBuilder().setType(TextType.MISSION).setText("Restableciendo Comunicaciones")
                    .build(),
                MissionResponse.GameText.newBuilder().setType(TextType.BOSS)
                    .setText("Bienvenido al Comando… que bueno que estas aquí. Yo se que es tu primer día pero tenemos un pequeño problema y no creo que tengamos tiempo para tu entrenamiento…Pero no  te preocupes, iras aprendiendo en el camino.")
                    .build(),
                MissionResponse.GameText.newBuilder().setType(TextType.BOSS)
                    .setText("Hoy en la mañana perdimos comunicación con nuestro Kotlinaut, al parecer es un pequeño problema con el tablero pero llevamos 2 horas sin saber nada de él y la verdad nos esta empezando a preocupar.  Ademas, si no logramos restablecer comunicación en los siguientes 20 minutos con el, no podremos terminar la misión.")
                    .build(),
                MissionResponse.GameText.newBuilder().setType(TextType.BOSS)
                    .setText("Tu primer tarea sera arreglar el tablero de comunicaciones, te ayudaría pero…surgió algo…Estos son algunos comandos básicos que te ayudaran, no tengo tiempo de explicarlos pero seguro sabras para que funcionan cada uno, bueno, debo correr.")
                    .build(),
                MissionResponse.GameText.newBuilder().setType(TextType.NARRATOR)
                    .setText("""
                        Puedes escribir 
                        
                        /manuales
                        
                        para consultar los manuales disponibles y las diferentes acciones disponibles para completar las misiones""".trimIndent())
                    .build(),
            )
        )
    )
)


