// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.NamedDomainObjectContainer

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
