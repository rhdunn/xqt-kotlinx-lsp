@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.NamedDomainObjectContainer

val <T> NamedDomainObjectContainer<T>.jsMain: T
    get() = findByName("jsMain")!!

val <T> NamedDomainObjectContainer<T>.jsTest: T
    get() = findByName("jsTest")!!
