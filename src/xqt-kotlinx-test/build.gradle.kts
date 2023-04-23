import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:${Version.Plugin.dokka}")
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.KotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.kotlinSerialization
    id("org.jetbrains.dokka") version Version.Plugin.dokka
    id("signing")
}

// region Kotlin Multiplatform (Common)

kotlin.sourceSets {
    commonMain.kotlin.srcDir("commonMain")
}

// endregion
// region Kotlin JVM

kotlin.jvm {
    compilations.all {
        kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget(project)
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
    rootProject.the<NodeJsRootExtension>().download = BuildConfiguration.nodeJsDownload(project)
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

@Suppress("KDocMissingDocumentation")
val nativeTarget = when (HostManager.host) {
    KonanTarget.MACOS_ARM64 -> kotlin.macosArm64("native")
    KonanTarget.MACOS_X64 -> kotlin.macosX64("native")
    KonanTarget.LINUX_X64 -> kotlin.linuxX64("native")
    KonanTarget.MINGW_X64 -> kotlin.mingwX64("native")
    else -> throw GradleException("Kotlin/Native build target '${HostManager.host.name}' is not supported.")
}

kotlin.sourceSets {
    nativeMain.kotlin.srcDir("nativeMain")
}

// endregion
