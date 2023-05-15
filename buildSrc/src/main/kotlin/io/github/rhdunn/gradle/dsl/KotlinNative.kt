// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import io.github.rhdunn.gradle.maven.SupportedVariants
import org.gradle.api.JavaVersion
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.konan.target.KonanTarget

/**
 * Access the native main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.nativeMain: T
    get() = findByName("nativeMain")!!

/**
 * Access the native main configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.nativeMain(target: Named): T {
    return findByName("${target.name}Main")!!
}

/**
 * Access the native test configuration object.
 */
val <T> NamedDomainObjectContainer<T>.nativeTest: T
    get() = findByName("nativeTest")!!

/**
 * Access the native test configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.nativeTest(target: Named): T {
    return findByName("${target.name}Test")!!
}

/**
 * Returns the default publication name for the target.
 */
val KonanTarget.publicationName: String
    get() = name.toLowerCase().replace("_", "")

/**
 * Returns the Maven artifact ID suffix name for the target.
 */
val KonanTarget.artifactIdSuffix: String
    get() = publicationName

/**
 * Compute the publication name for the specified Konan target.
 */
fun SupportedVariants.nativePublication(
    konanTarget: KonanTarget,
    konanBuildTarget: KonanTarget
): String? = when (this) {
    SupportedVariants.All -> konanTarget.publicationName
    SupportedVariants.None -> null
    SupportedVariants.TargetOnly -> when (konanTarget) {
        konanBuildTarget -> "native"
        else -> null
    }
}

/**
 * Returns the Maven artifact ID for the native target.
 */
fun Project.nativeArtifactId(target: KonanTarget): String = "$name-${target.artifactIdSuffix}"
