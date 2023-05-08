// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.maven

/**
 * The build type associated with the project version.
 *
 * @param suffix The suffix applied to the version tag.
 */
enum class BuildType(val suffix: String) {
    /**
     * A release build.
     */
    Release(""),

    /**
     * A development snapshot build.
     */
    Snapshot("-SNAPSHOT"),
}
