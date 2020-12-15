import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.protobuf") version "0.8.14"
}

//group = "com.kotlinerskt.kotlinaut"

dependencies {
    protobuf(project(":proto"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    api("com.google.protobuf:protobuf-javalite:3.14.0")
    api("io.grpc:grpc-kotlin-stub-lite:1.0.0")
}

//sourceSets {
//    main {
//        java {
//            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/java")
//            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc")
//            srcDir("${protobuf.protobuf.generatedFilesBaseDir}/main/grpckt")
//        }
//    }
//}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }

    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }

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
                id("java") {
                    option("lite")
                }
                id("grpc") {
                    option("lite")
                }
                id("grpckt") {
                    option("lite")
                }
            }
        }
    }
}
