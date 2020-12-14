import com.google.protobuf.gradle.*
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.google.protobuf")
}

dependencies {
    protobuf(project(":proto"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    api("com.google.protobuf:protobuf-java-util:3.14.0")
    api("io.grpc:grpc-kotlin-stub:1.0.0")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }

    plugins {
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0"
        }

//        id("grpc") {
//            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
//        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("gprckt")
            }
        }
    }
}

tasks {
    withType<KotlinCompile> {
        all {
            sourceCompatibility = VERSION_1_8.toString()
        }
    }
}
