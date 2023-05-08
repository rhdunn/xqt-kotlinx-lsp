// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.dsl

import org.gradle.api.NamedDomainObjectContainer

/**
 * Access the common main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.commonMain: T
    get() = findByName("commonMain")!!

/**
 * Access the common test configuration object.
 */
val <T> NamedDomainObjectContainer<T>.commonTest: T
    get() = findByName("commonTest")!!
