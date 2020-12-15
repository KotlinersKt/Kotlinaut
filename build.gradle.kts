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
