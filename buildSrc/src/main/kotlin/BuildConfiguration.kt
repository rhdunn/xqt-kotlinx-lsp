@Suppress("KDocMissingDocumentation")
object BuildConfiguration {
    /**
     * The version of the JVM to target by the Kotlin compiler.
     */
    val jvmTarget: String
        get() = System.getProperty("jvm.target") ?: "11"
}
