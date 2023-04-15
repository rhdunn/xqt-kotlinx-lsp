@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.NamedDomainObjectContainer

val <T> NamedDomainObjectContainer<T>.nativeMain: T
    get() = findByName("nativeMain")!!
