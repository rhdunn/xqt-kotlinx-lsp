plugins {
    kotlin("multiplatform") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("maven-publish")
}

group = "xqt-kotlinx-lsp"
version = "0.1-SNAPSHOT"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = System.getProperty("jvm.target") ?: "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform() // JUnit 5
        }
    }

    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    when (System.getProperty("js.browser")) {
                        "Chrome" -> useChromeHeadless()
                        "Chrome Canary" -> useChromeCanaryHeadless()
                        "Chromium" -> useChromiumHeadless()
                        "Firefox" -> useFirefoxHeadless()
                        "Firefox Aurora" -> useFirefoxAuroraHeadless()
                        "Firefox Developer" -> useFirefoxDeveloperHeadless()
                        "Firefox Nightly" -> useFirefoxNightlyHeadless()
                        "Phantom JS" -> usePhantomJS()
                        "Safari" -> useSafari()
                        else -> when (System.getProperty("os.name")) {
                            "Mac OS X" -> useSafari()
                            else -> useFirefoxHeadless()
                        }
                    }
                }
            }
        }

        nodejs {
        }
    }

    val hostOs = System.getProperty("os.name")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("commonMain")
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        val commonTest by getting {
            kotlin.srcDir("commonTest")
            dependencies {
                implementation(kotlin("test"))
                implementation(project(":src:xqt-kotlinx-test"))
            }
        }

        val jvmMain by getting {
            kotlin.srcDir("jvmMain")
        }
        val jvmTest by getting {
            kotlin.srcDir("jvmTest")
        }

        val jsMain by getting {
            kotlin.srcDir("jsMain")
        }
        val jsTest by getting {
            kotlin.srcDir("jsTest")
        }

        val nativeMain by getting {
            kotlin.srcDir("nativeMain")
        }
        val nativeTest by getting {
            kotlin.srcDir("nativeTest")
        }
    }
}
