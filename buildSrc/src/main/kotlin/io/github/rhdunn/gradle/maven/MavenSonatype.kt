// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.maven

import ProjectMetadata

/**
 * The Maven Central (Sonatype) artifact publication location.
 *
 * @param url The url to publish the Maven artifacts to.
 */
enum class MavenSonatype(val url: String?) {
    /**
     * Don't publish the builds to Maven Central.
     */
    None(null),

    /**
     * Publish builds to the release repositories.
     */
    Release(ProjectMetadata.MavenSonatype.ReleaseStaging),

    /**
     * Publish builds to the snapshot repositories.
     */
    Snapshot(ProjectMetadata.MavenSonatype.Snapshot),
}
