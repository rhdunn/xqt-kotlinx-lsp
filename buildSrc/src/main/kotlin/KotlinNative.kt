// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.NamedDomainObjectContainer

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
