@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.NamedDomainObjectContainer

val <T> NamedDomainObjectContainer<T>.commonMain: T
    get() = findByName("commonMain")!!
