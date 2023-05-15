// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import BuildConfiguration
import io.github.rhdunn.gradle.maven.SupportedVariants
import org.gradle.api.JavaVersion
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * Compute the publication name for the specified Java version.
 */
fun jvmName(javaVersion: JavaVersion, suffix: String = ""): String =
    "jvm${javaVersion.majorVersion}$suffix"

/**
 * Compute the publication name for the specified Java version.
 */
fun SupportedVariants.jvmName(jvmTarget: JavaVersion, javaVersion: JavaVersion): String? = when (this) {
    SupportedVariants.All -> jvmName(jvmTarget, suffix = "")
    SupportedVariants.None -> null
    SupportedVariants.TargetOnly -> when (jvmTarget) {
        javaVersion -> "jvm"
        else -> null
    }
}

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
 * Access the JVM main configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.jvmMain(target: Named): T {
    return findByName("${target.name}Main")!!
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
 * Access the JVM test configuration object.
 */
fun <T> NamedDomainObjectContainer<T>.jvmTest(target: Named): T {
    return findByName("${target.name}Test")!!
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
