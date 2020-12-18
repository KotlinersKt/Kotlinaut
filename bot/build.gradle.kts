import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

tasks {
    withType<KotlinCompile>().all {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }
}


dependencies {
    implementation(project(":shared"))
    implementation("com.github.kotlin-telegram-bot.kotlin-telegram-bot:dispatcher:6.0.1")
}

task("testBot", JavaExec::class){
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.kotlinerskt.kotlinaut.bot.BotClientKt")
}
