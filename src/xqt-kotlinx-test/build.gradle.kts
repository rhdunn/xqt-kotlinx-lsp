// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
@file:Suppress("KDocMissingDocumentation")

import io.github.rhdunn.gradle.dsl.*
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
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

val javaVersion = BuildConfiguration.javaVersion(project)
if (javaVersion !in ProjectMetadata.BuildTargets.JvmTargets)
    throw GradleException("The specified jvm.target is not in the configured project metadata.")

ProjectMetadata.BuildTargets.JvmTargets.forEach { jvmTarget ->
    kotlin.jvm(jvmName(jvmTarget)) {
        compilations.all {
            kotlinOptions.jvmTarget = jvmTarget.toString()
        }

        if (jvmTarget == javaVersion) {
            withJava()
        }

        attributes {
            attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, jvmTarget.majorVersion.toInt())
        }
    }
}

if (ProjectMetadata.BuildTargets.JvmTargets.isNotEmpty()) {
    kotlin.sourceSets {
        jvmMain(javaVersion).kotlin.srcDir("jvmMain")

        jvmMain(javaVersion).dependencies {
            implementation(Dependency.JUnitJupiterApi)
        }
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
val nativeTarget: KotlinNativeTarget = when (BuildConfiguration.konanTarget(project)) {
    KonanTarget.ANDROID_ARM32 -> kotlin.androidNativeArm32("androidarm32") // Tier 3
    KonanTarget.ANDROID_ARM64 -> kotlin.androidNativeArm64("androidarm64") // Tier 3
    KonanTarget.ANDROID_X64 -> kotlin.androidNativeX64("androidx64") // Tier 3
    KonanTarget.ANDROID_X86 -> kotlin.androidNativeX86("androidx86") // Tier 3
    KonanTarget.IOS_ARM32 -> kotlin.iosArm32("iosarm32") // Deprecated, to be removed in 1.9.20
    KonanTarget.IOS_ARM64 -> kotlin.iosArm64("iosarm64") // Tier 2
    KonanTarget.IOS_SIMULATOR_ARM64 -> kotlin.iosSimulatorArm64("iossimulatorarm64") // Tier 1
    KonanTarget.IOS_X64 -> kotlin.iosX64("iosx64") // Tier 1
    KonanTarget.LINUX_ARM32_HFP -> kotlin.linuxArm32Hfp("linuxarm32hfp") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_ARM64 -> kotlin.linuxArm64("linuxarm64") // Tier 2
    KonanTarget.LINUX_MIPS32 -> kotlin.linuxMips32("linuxmips32") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_MIPSEL32 -> kotlin.linuxMipsel32("linuxmipsel32") // Deprecated, to be removed in 1.9.20
    KonanTarget.LINUX_X64 -> kotlin.linuxX64("linuxx64") // Tier 1 ; native host
    KonanTarget.MACOS_ARM64 -> kotlin.macosArm64("macosarm64") // Tier 1 ; native host
    KonanTarget.MACOS_X64 -> kotlin.macosX64("macosx64") // Tier 1 ; native host
    KonanTarget.MINGW_X64 -> kotlin.mingwX64("mingwx64") // Tier 3 ; native host
    KonanTarget.MINGW_X86 -> kotlin.mingwX86("mingwx86") // Deprecated, to be removed in 1.9.20
    KonanTarget.TVOS_ARM64 -> kotlin.tvosArm64("tvosarm64") // Tier 2
    KonanTarget.TVOS_SIMULATOR_ARM64 -> kotlin.tvosSimulatorArm64("tvossimulatorarm64") // Tier 2
    KonanTarget.TVOS_X64 -> kotlin.tvosX64("tvosx64") // Tier 2
    KonanTarget.WASM32 -> kotlin.wasm32("wasm32") // Deprecated, to be removed in 1.9.20
    KonanTarget.WATCHOS_ARM32 -> kotlin.watchosArm32("watchosarm32") // Tier 2
    KonanTarget.WATCHOS_ARM64 -> kotlin.watchosArm64("watchosarm64") // Tier 2
    KonanTarget.WATCHOS_SIMULATOR_ARM64 -> kotlin.watchosSimulatorArm64("watchossimulatorarm64") // Tier 2
    KonanTarget.WATCHOS_X64 -> kotlin.watchosX64("watchosx64") // Tier 2
    KonanTarget.WATCHOS_X86 -> kotlin.watchosX86("watchosx86") // Deprecated, to be removed in 1.9.20
    is KonanTarget.ZEPHYR -> throw GradleException("Kotlin/Native build target 'zephyr' is not supported.")
}

kotlin.sourceSets {
    nativeMain(nativeTarget).kotlin.srcDir("nativeMain")
}

// endregion
// region Documentation

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        suppress.set(true)
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        suppress.set(true)
    }
}

// endregion
