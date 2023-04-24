// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Versions of the various plugins and libraries used by the project.
 */
@Suppress("KDocMissingDocumentation")
object Version {
    /**
     * The version of the Kotlin compiler and runtime.
     */
    const val Kotlin = "1.7.20"

    /**
     * Versions of the various plugins used by the project.
     */
    object Plugin {
        /**
         * The version of the `id("org.jetbrains.dokka")` plugin.
         */
        const val Dokka = Kotlin

        /**
         * The version of the `kotlin("multiplatform")` plugin.
         */
        const val KotlinMultiplatform = Kotlin

        const val dokka = Kotlin
        const val kotlinSerialization = Kotlin
    }

    /**
     * Versions of the various libraries used by the project.
     */
    object Dependency {
        /**
         * The version of the `dokka-base` library.
         */
        const val DokkaBase = Plugin.Dokka

        const val junit = "5.6.0"
        const val kotlinSerialization = "1.4.1"
        const val xqtJsonRpc = "1.0.0"
    }
}
