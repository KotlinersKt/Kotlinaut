import com.google.protobuf.gradle.*
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    id("com.google.protobuf")
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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
    }

    plugins {
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.0.0:jdk7@jar"
        }

        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.34.0"
        }
    }

    generatedFilesBaseDir = "${project.buildDir}/gen/src"

    generateProtoTasks {
        all().forEach { task ->
            task.plugins.register("grpc")
            task.plugins.register("grpckt")
        }
    }
}

//sourceSets {
//    main {
//        java {
//            srcDir("build/generated/source/proto/main/java")
//            srcDir("build/generated/source/proto/main/grpc")
//        }
//    }
//}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

//kotlin {
//    sourceSets {
//        main {
//            this.kotlin.srcDir("build/generated/source/proto/main/grpckt")
//        }
//    }
//}

tasks {
    withType<KotlinCompile> {
        all {
            sourceCompatibility = VERSION_1_8.toString()
            targetCompatibility = VERSION_1_8.toString()
        }
    }
}
