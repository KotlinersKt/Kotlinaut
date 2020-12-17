rootProject.name = "Kotlinaut"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:4.0.1")
            }
        }
    }
}

include("proto")
include("stub", "stub-android")
include("shared")
include("server")
include("bot")
include("android", "ios")
include("web")

enableFeaturePreview("GRADLE_METADATA")
