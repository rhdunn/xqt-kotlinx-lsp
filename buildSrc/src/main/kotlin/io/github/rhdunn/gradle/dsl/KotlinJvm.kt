// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import BuildConfiguration
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * Compute the publication name for the specified Java version.
 */
fun jvmName(javaVersion: JavaVersion, suffix: String = ""): String =
    "jvm${javaVersion.majorVersion}$suffix"

/**
 * Access the JVM main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.jvmMain: T
    get() = findByName("jvmMain")!!

/**
 * Access the JVM main configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.jvmMain(javaVersion: JavaVersion): T {
    return findByName(jvmName(javaVersion, "Main"))!!
}

/**
 * Access the JVM test configuration object.
 */
val <T> NamedDomainObjectContainer<T>.jvmTest: T
    get() = findByName("jvmTest")!!

/**
 * Access the JVM test configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.jvmTest(javaVersion: JavaVersion): T {
    return findByName(jvmName(javaVersion, "Test"))!!
}

/**
 * Returns the Maven artifact ID for the JVM target.
 */
val Project.jvmArtifactId: String
    get() = jvmArtifactId(BuildConfiguration.javaVersion(project))

/**
 * Returns the Maven artifact ID for the JVM target.
 */
fun Project.jvmArtifactId(javaVersion: JavaVersion): String {
    return "$name-jvm${javaVersion.majorVersion}"
}
