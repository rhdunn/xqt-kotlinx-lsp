// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.konan.target.KonanTarget

/**
 * Access the native main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.nativeMain: T
    get() = findByName("nativeMain")!!

/**
 * Access the native test configuration object.
 */
val <T> NamedDomainObjectContainer<T>.nativeTest: T
    get() = findByName("nativeTest")!!

/**
 * Returns the Maven artifact ID suffix name for the target.
 */
val KonanTarget.artifactIdSuffix: String
    get() = name.toLowerCase().replace("_", "")

/**
 * Returns the Maven artifact ID for the native target.
 */
fun Project.nativeArtifactId(target: KonanTarget): String = "$name-${target.artifactIdSuffix}"
