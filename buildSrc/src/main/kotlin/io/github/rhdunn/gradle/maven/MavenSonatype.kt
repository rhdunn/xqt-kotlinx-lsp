// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.maven

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
    Release("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"),

    /**
     * Publish builds to the snapshot repositories.
     */
    Snapshot("https://s01.oss.sonatype.org/content/repositories/snapshots/"),
}
