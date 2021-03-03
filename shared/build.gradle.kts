import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js(IR) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                outputFileName = "kotlinaut-lib.js"
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }

    sourceSets {
//        all {
//            languageSettings.apply {
//                useExperimentalAnnotation("kotlin.RequiresOptIn")
//                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
//                useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
//            }
//        }

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(project(":stub"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

                api("com.google.protobuf:protobuf-java-util:3.14.0")

                runtimeOnly("io.grpc:grpc-netty:1.34.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.7.0")

                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains:kotlin-react:17.0.0-pre.129-kotlin-1.4.20")
                implementation("org.jetbrains:kotlin-react-dom:17.0.0-pre.129-kotlin-1.4.20")
                implementation("org.jetbrains:kotlin-styled:5.2.0-pre.129-kotlin-1.4.20")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val iosArm64Main by getting
        val iosArm64Test by getting

        val androidMain by getting {
            dependencies {
                api(project(":stub-android"))

                api("io.grpc:grpc-okhttp:1.34.1")
                api("io.grpc:grpc-protobuf-lite:1.34.0")
                api("io.grpc:grpc-stub:1.34.0")
                api("javax.annotation:javax.annotation-api:1.3.2")

                api("androidx.appcompat:appcompat:1.2.0")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
    }
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)

        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

tasks {
    val packForXcode by creating(Sync::class) {
        group = "build"
        val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
        val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
        val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
        val framework =
            kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
        inputs.property("mode", mode)
        dependsOn(framework.linkTask)
        val targetDir = File(buildDir, "xcode-frameworks")
        from({ framework.outputDirectory })
        into(targetDir)
    }

    getByName("build").dependsOn(packForXcode)

    getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
        outputFileName = "output.js"
    }
}
