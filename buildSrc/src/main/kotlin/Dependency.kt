// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Versions of the various plugins and libraries used by the project.
 */
object Dependency {
    /**
     * The `dokka-base` library.
     *
     * `SPDX-License-Identifier: Apache-2.0`
     *
     * @see <a href="https://github.com/Kotlin/dokka/tree/master/plugins/base">https://github.com/Kotlin/dokka/tree/master/plugins/base</a>
     */
    val DokkaBase = "org.jetbrains.dokka:dokka-base:${Version.Dependency.DokkaBase}"

    /**
     * The `junit-jupiter-api` library.
     *
     * `SPDX-License-Identifier: EPL-2.0` (Eclipse Public License 2.0)
     *
     * @see <a href="https://github.com/junit-team/junit5/tree/main/junit-jupiter-api">https://github.com/junit-team/junit5/tree/main/junit-jupiter-api</a>
     */
    val JUnitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Version.Dependency.JUnit}"
}
