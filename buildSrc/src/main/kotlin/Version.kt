// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Versions of the various plugins and libraries used by the project.
 */
@Suppress("KDocMissingDocumentation")
object Version {
    /**
     * The version of the Kotlin compiler and runtime.
     *
     * `SPDX-License-Identifier: Apache-2.0`
     *
     * @see <a href="https://github.com/JetBrains/kotlin">https://github.com/JetBrains/kotlin</a>
     */
    const val Kotlin = "1.8.21"

    /**
     * Versions of the various plugins used by the project.
     */
    object Plugin {
        /**
         * The version of the `id("org.jetbrains.dokka")` plugin.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/Kotlin/dokka">https://github.com/Kotlin/dokka</a>
         */
        const val Dokka = "1.8.10"

        /**
         * The version of the `kotlin("multiplatform")` plugin.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin">https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin</a>
         */
        const val KotlinMultiplatform = Kotlin

        /**
         * The version of the `kotlin("plugin.serialization")` plugin.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/JetBrains/kotlin/tree/master/plugins/kotlinx-serialization">https://github.com/JetBrains/kotlin/tree/master/plugins/kotlinx-serialization</a>
         */
        const val KotlinSerialization = Kotlin
    }

    /**
     * Versions of the various libraries used by the project.
     */
    object Dependency {
        /**
         * The version of the `dokka-base` library.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/Kotlin/dokka/tree/master/plugins/base">https://github.com/Kotlin/dokka/tree/master/plugins/base</a>
         */
        const val DokkaBase = Plugin.Dokka

        /**
         * The version of the `junit` library.
         *
         * `SPDX-License-Identifier: EPL-2.0` (Eclipse Public License 2.0)
         *
         * @see <a href="https://github.com/junit-team/junit5">https://github.com/junit-team/junit5</a>
         */
        const val JUnit = "5.9.3"

        /**
         * The version of the `kotlinx-serialization-json` library.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/Kotlin/kotlinx.serialization/tree/master/formats/json">https://github.com/Kotlin/kotlinx.serialization/tree/master/formats/json</a>
         */
        const val KotlinxSerializationJson = "1.5.1"

        /**
         * The version of the `xqt-kotlinx-json-rpc` library.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/rhdunn/xqt-kotlinx-json-rpc">https://github.com/rhdunn/xqt-kotlinx-json-rpc</a>
         */
        const val XqtKotlinxJsonRpc = "1.0.3"
    }
}
