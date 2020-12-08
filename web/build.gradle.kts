import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("js")
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation(project(":shared"))

    implementation("org.jetbrains:kotlin-react:17.0.0-pre.129-kotlin-1.4.20")
    implementation("org.jetbrains:kotlin-react-dom:17.0.0-pre.129-kotlin-1.4.20")
    implementation("org.jetbrains:kotlin-styled:5.2.0-pre.129-kotlin-1.4.20")

    testImplementation(kotlin("test-js"))
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
                outputFileName = "kotlinaut.js"
            }
            runTask {
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
}
