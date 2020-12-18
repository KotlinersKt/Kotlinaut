buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin","1.4.21"))
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
    }
}

group = "com.kotlinerskt"
version = "0.1.0"

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}
