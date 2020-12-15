import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    id("com.google.protobuf") version "0.8.14"
}

group = "com.kotlinerskt.kotlinaut"

dependencies {
    protobuf(project(":proto"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    api("com.google.protobuf:protobuf-java-util:3.14.0")
    api("io.grpc:grpc-stub:1.34.0")
    api("io.grpc:grpc-protobuf:1.34.0")
    api("io.grpc:grpc-kotlin-stub:1.0.0")
}

sourceSets {
    main {
        java {
            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/java")
            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc")
            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/grpckt")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }

        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

tasks {
    withType<KotlinCompile> {
        all {
            sourceCompatibility = JavaVersion.VERSION_1_7.toString()
            targetCompatibility = JavaVersion.VERSION_1_7.toString()
        }
    }
}
