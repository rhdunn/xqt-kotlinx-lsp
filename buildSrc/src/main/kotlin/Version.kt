// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Versions of the various plugins and libraries used by the project.
 */
object Version {
    /**
     * The version of the Kotlin compiler and runtime.
     *
     * `SPDX-License-Identifier: Apache-2.0`
     *
     * @see <a href="https://github.com/JetBrains/kotlin">https://github.com/JetBrains/kotlin</a>
     */
    const val Kotlin = "1.7.20"

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
        const val Dokka = Kotlin

        /**
         * The version of the `kotlin("multiplatform")` plugin.
         *
         * `SPDX-License-Identifier: Apache-2.0`
         *
         * @see <a href="https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin">https://github.com/JetBrains/kotlin/tree/master/libraries/tools/kotlin-gradle-plugin</a>
         */
        const val KotlinMultiplatform = Kotlin
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
        const val JUnit = "5.6.0"
    }
}
