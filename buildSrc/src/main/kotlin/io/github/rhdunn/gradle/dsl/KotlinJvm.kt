// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import BuildConfiguration
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * Access the JVM main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.jvmMain: T
    get() = findByName("jvmMain")!!

/**
 * Access the JVM test configuration object.
 */
val <T> NamedDomainObjectContainer<T>.jvmTest: T
    get() = findByName("jvmTest")!!

/**
 * Returns the Maven artifact ID for the JVM target.
 */
val Project.jvmArtifactId: String
    get() = "$name-jvm${BuildConfiguration.javaVersion(project).majorVersion}"
