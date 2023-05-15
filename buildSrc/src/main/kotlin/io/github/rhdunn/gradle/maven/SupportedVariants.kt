// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.maven

/**
 * The variants available for a given multiplatform target (jvm, js, native).
 */
enum class SupportedVariants {
    /**
     * All available variants for this target.
     */
    All,

    /**
     * The single target as specified via the command-line configuration.
     */
    TargetOnly,

    /**
     * None of the variants for this target.
     */
    None,
}
