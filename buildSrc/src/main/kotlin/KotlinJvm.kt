@file:Suppress("KDocMissingDocumentation")

import org.gradle.api.NamedDomainObjectContainer

val <T> NamedDomainObjectContainer<T>.jvmMain: T
    get() = findByName("jvmMain")!!

val <T> NamedDomainObjectContainer<T>.jvmTest: T
    get() = findByName("jvmTest")!!
