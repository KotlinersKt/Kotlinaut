plugins {
    kotlin("jvm")
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

dependencies {
    implementation(project(":shared"))
    implementation("com.github.kotlin-telegram-bot.kotlin-telegram-bot:dispatcher:6.0.1")
}
