// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.maven

/**
 * The method by which artifacts are signed for Maven publications.
 */
enum class ArtifactSigningMethod {
    /**
     * The artifacts will not be signed.
     */
    None,

    /**
     * The artifacts will be signed via the `gpg` command.
     */
    GpgCommand,

    /**
     * The artifacts will be signed via in-memory key data from the project/environment.
     */
    Environment,
}
