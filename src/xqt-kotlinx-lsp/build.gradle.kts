import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

plugins {
    kotlin("multiplatform") version Version.Plugin.kotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
    id("maven-publish")
}

group = "io.github.rhdunn"
version = "0.1-SNAPSHOT"

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.downloadNodeJs
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget
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
                    when (BuildConfiguration.jsBrowser) {
                        JsBrowser.Chrome -> useChromeHeadless()
                        JsBrowser.ChromeCanary -> useChromeCanaryHeadless()
                        JsBrowser.Chromium -> useChromiumHeadless()
                        JsBrowser.Firefox -> useFirefoxHeadless()
                        JsBrowser.FirefoxAurora -> useFirefoxAuroraHeadless()
                        JsBrowser.FirefoxDeveloper -> useFirefoxDeveloperHeadless()
                        JsBrowser.FirefoxNightly -> useFirefoxNightlyHeadless()
                        JsBrowser.PhantomJs -> usePhantomJS()
                        JsBrowser.Safari -> useSafari()
                    }
                }
            }
        }

        nodejs {
        }
    }

    val nativeTarget = when (BuildConfiguration.hostOs) {
        HostOs.Windows -> mingwX64("native")
        HostOs.Linux -> linuxX64("native")
        HostOs.MacOsX -> macosX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        commonMain.kotlin.srcDir("commonMain")
        commonTest.kotlin.srcDir("commonTest")

        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.Dependency.kotlinSerialization}")
            implementation("io.github.rhdunn:xqt-kotlinx-json-rpc:${Version.Dependency.xqtJsonRpc}")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(project(":src:xqt-kotlinx-test"))
        }

        jvmMain.kotlin.srcDir("jvmMain")

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
