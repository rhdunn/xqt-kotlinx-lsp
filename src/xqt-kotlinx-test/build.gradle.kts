import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

buildscript {
    dependencies {
        classpath(Dependency.DokkaBase)
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.KotlinMultiplatform
    kotlin("plugin.serialization") version Version.Plugin.KotlinSerialization
    id("org.jetbrains.dokka") version Version.Plugin.Dokka
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
        implementation(Dependency.JUnitJupiterApi)
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

// https://kotlinlang.org/docs/native-target-support.html
@Suppress("KDocMissingDocumentation")
val nativeTarget = when (BuildConfiguration.konanTarget(project)) {
    KonanTarget.ANDROID_ARM32 -> kotlin.androidNativeArm32("native") // Tier 3
    KonanTarget.ANDROID_ARM64 -> kotlin.androidNativeArm64("native") // Tier 3
    KonanTarget.ANDROID_X64 -> kotlin.androidNativeX64("native") // Tier 3
    KonanTarget.ANDROID_X86 -> kotlin.androidNativeX86("native") // Tier 3
    KonanTarget.IOS_ARM32 -> kotlin.iosArm32("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.IOS_ARM64 -> kotlin.iosArm64("native") // Tier 2
    KonanTarget.IOS_SIMULATOR_ARM64 -> kotlin.iosSimulatorArm64("native") // Tier 1
    KonanTarget.IOS_X64 -> kotlin.iosX64("native") // Tier 1
    KonanTarget.LINUX_ARM32_HFP -> kotlin.linuxArm32Hfp("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_ARM64 -> kotlin.linuxArm64("native") // Tier 2
    KonanTarget.LINUX_MIPS32 -> kotlin.linuxMips32("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_MIPSEL32 -> kotlin.linuxMipsel32("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_X64 -> kotlin.linuxX64("native") // Tier 1 ; native host
    KonanTarget.MACOS_ARM64 -> kotlin.macosArm64("native") // Tier 1 ; native host
    KonanTarget.MACOS_X64 -> kotlin.macosX64("native") // Tier 1 ; native host
    KonanTarget.MINGW_X64 -> kotlin.mingwX64("native") // Tier 3 ; native host
    KonanTarget.MINGW_X86 -> kotlin.mingwX86("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.TVOS_ARM64 -> kotlin.tvosArm64("native") // Tier 2
    KonanTarget.TVOS_SIMULATOR_ARM64 -> kotlin.tvosSimulatorArm64("native") // Tier 2
    KonanTarget.TVOS_X64 -> kotlin.tvosX64("native") // Tier 2
    KonanTarget.WASM32 -> kotlin.wasm32("native") // Deprecated, to be removed in 1.9.20
    KonanTarget.WATCHOS_ARM32 -> kotlin.watchosArm32("native") // Tier 2
    KonanTarget.WATCHOS_ARM64 -> kotlin.watchosArm64("native") // Tier 2
    KonanTarget.WATCHOS_SIMULATOR_ARM64 -> kotlin.watchosSimulatorArm64("native") // Tier 2
    KonanTarget.WATCHOS_X64 -> kotlin.watchosX64("native") // Tier 2
    KonanTarget.WATCHOS_X86 -> kotlin.watchosX86("native") // Deprecated, to be removed in 1.9.20
    is KonanTarget.ZEPHYR -> throw GradleException("Kotlin/Native build target 'zephyr' is not supported.")
}

kotlin.sourceSets {
    nativeMain.kotlin.srcDir("nativeMain")
}

// endregion
