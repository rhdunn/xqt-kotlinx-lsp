@Suppress("KDocMissingDocumentation")
object BuildConfiguration {
    /**
     * The version of the JVM to target by the Kotlin compiler.
     */
    val jvmTarget: String
        get() = System.getProperty("jvm.target") ?: "11"

    /**
     * Should the build process download node-js if it is not present? (default: true)
     */
    val downloadNodeJs: Boolean
        get() = System.getProperty("nodejs.download") != "false"

    /**
     * The name of the Operating System the build is running on.
     */
    val hostOsName: String
        get() = System.getProperty("os.name") ?: "Unknown"
}
