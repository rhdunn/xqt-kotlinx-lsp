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

        /**
         * The version of the `kotlin("plugin.serialization")` plugin.
         */
        const val KotlinSerialization = Kotlin
    }

    /**
     * Versions of the various libraries used by the project.
     */
    object Dependency {
        /**
         * The version of the `dokka-base` library.
         */
        const val DokkaBase = Plugin.Dokka

        /**
         * The version of the `junit` library.
         */
        const val JUnit = "5.6.0"

        /**
         * The version of the `kotlinx-serialization-json` library.
         */
        const val KotlinxSerializationJson = "1.4.1"

        /**
         * The version of the `xqt-kotlinx-json-rpc` library.
         */
        const val XqtKotlinxJsonRpc = "1.0.0"
    }
}
