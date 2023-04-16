@Suppress("KDocMissingDocumentation")
object Version {
    const val kotlin = "1.7.20"

    object Plugin {
        const val dokka = kotlin
        const val kotlinMultiplatform = kotlin
        const val kotlinSerialization = kotlin
    }

    object Dependency {
        const val junit = "5.6.0"
        const val kotlinSerialization = "1.4.1"
        const val xqtJsonRpc = "1.0.0"
    }
}
