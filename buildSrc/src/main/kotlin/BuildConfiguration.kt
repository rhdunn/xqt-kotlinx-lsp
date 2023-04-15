@Suppress("KDocMissingDocumentation", "MemberVisibilityCanBePrivate")
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
     * The Operating System the build is running on.
     */
    val hostOs: HostOs
        get() = HostOs(System.getProperty("os.name") ?: "Unknown")

    /**
     * The headless web browser to run the JS tests on.
     */
    val jsBrowser: JsBrowser
        get() = JsBrowser(System.getProperty("js.browser"), hostOs)
}
