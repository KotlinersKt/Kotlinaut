import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    kotlin("kapt")
}

repositories {
    maven {
        url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/")
    }
}

group = "com.kotlinerskt.kotlinaut.server"
version = "0.1.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

kotlin {
    experimental {

    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile>().all {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    implementation("io.arrow-kt:arrow-core:0.11.0")
    implementation("io.arrow-kt:arrow-syntax:0.11.0")
    kapt("io.arrow-kt:arrow-meta:0.11.0")

    runtimeOnly("io.grpc:grpc-netty:1.34.0")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.7.0")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.7.0")

    testImplementation("io.kotest:kotest-runner-junit5:4.3.1")
    testImplementation("io.kotest:kotest-assertions-core:4.3.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}


task("runServer", JavaExec::class) {
    dependsOn("classes")
    classpath = sourceSets["main"].runtimeClasspath

    mainClass.set("com.kotlinerskt.kotlinaut.control.KotlinautGameServerKt")
}
