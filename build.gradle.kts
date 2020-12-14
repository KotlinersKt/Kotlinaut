buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin","1.4.21"))
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

plugins {
    id("com.google.protobuf") version "0.8.14" apply false
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
