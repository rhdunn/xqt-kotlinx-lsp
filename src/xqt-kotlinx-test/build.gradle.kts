plugins {
    kotlin("multiplatform") version Version.Plugin.kotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().download =
        System.getProperty("nodejs.download") != "false"
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget
        }
        withJava()
    }

    js(BOTH) {
        browser {
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
        }

        val jvmMain by getting {
            kotlin.srcDir("jvmMain")
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter-api:${Version.Dependency.junit}")
            }
        }

        val jsMain by getting {
            kotlin.srcDir("jsMain")
        }

        val nativeMain by getting {
            kotlin.srcDir("nativeMain")
        }
    }
}
