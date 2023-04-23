// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.NamedDomainObjectContainer

/**
 * Access the JS main configuration object.
 */
val <T> NamedDomainObjectContainer<T>.jsMain: T
    get() = findByName("jsMain")!!
