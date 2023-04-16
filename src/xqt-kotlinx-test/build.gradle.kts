import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

plugins {
    kotlin("multiplatform") version Version.Plugin.kotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
}

// region Kotlin Multiplatform (Common)

kotlin.sourceSets {
    commonMain.kotlin.srcDir("commonMain")
}

// endregion
// region Kotlin JVM

kotlin.jvm {
    compilations.all {
        kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget
    }

    withJava()
}

kotlin.sourceSets {
    jvmMain.kotlin.srcDir("jvmMain")

    jvmMain.dependencies {
        implementation("org.junit.jupiter:junit-jupiter-api:${Version.Dependency.junit}")
    }
}

// endregion
// region Kotlin JS

rootProject.plugins.withType<NodeJsRootPlugin> {
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.downloadNodeJs
}

kotlin.js(KotlinJsCompilerType.BOTH).browser {
}

kotlin.js(KotlinJsCompilerType.BOTH).nodejs {
}

kotlin.sourceSets {
    jsMain.kotlin.srcDir("jsMain")
}

// endregion
// region Kotlin Native

when (BuildConfiguration.hostOs) {
    HostOs.Windows -> kotlin.mingwX64("native")
    HostOs.Linux -> kotlin.linuxX64("native")
    HostOs.MacOsX -> kotlin.macosX64("native")
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
}

kotlin.sourceSets {
    nativeMain.kotlin.srcDir("nativeMain")
}

// endregion
