@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.NamedDomainObjectContainer

val <T> NamedDomainObjectContainer<T>.jvmMain: T
    get() = findByName("jvmMain")!!
