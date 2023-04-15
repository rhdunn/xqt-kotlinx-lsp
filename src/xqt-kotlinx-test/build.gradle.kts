import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

plugins {
    kotlin("multiplatform") version Version.Plugin.kotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
}

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.downloadNodeJs
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

    val nativeTarget = when (BuildConfiguration.hostOs) {
        HostOs.Windows -> mingwX64("native")
        HostOs.Linux -> linuxX64("native")
        HostOs.MacOsX -> macosX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        commonMain.kotlin.srcDir("commonMain")
        jvmMain.kotlin.srcDir("jvmMain")

        jvmMain.dependencies {
            implementation("org.junit.jupiter:junit-jupiter-api:${Version.Dependency.junit}")
        }

        val jsMain by getting {
            kotlin.srcDir("jsMain")
        }

        val nativeMain by getting {
            kotlin.srcDir("nativeMain")
        }
    }
}
