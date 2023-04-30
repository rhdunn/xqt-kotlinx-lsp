// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

buildscript {
    dependencies {
        classpath(Dependency.DokkaBase)
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.KotlinMultiplatform
    id("org.jetbrains.dokka") version Version.Plugin.Dokka
}

// Workaround for generating documentation. The root project needs kotlin to be
// configured for at least one multiplatform target, otherwise dokka will report
// "Nothing to document" in the dokkaHtml and dokkaHtmlPartial tasks.
kotlin {
    jvm()
}

tasks.withType<DokkaMultiModuleTask>().configureEach {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "Copyright © ${ProjectMetadata.Copyright.Year} ${ProjectMetadata.Copyright.Owner}"
    }
}
