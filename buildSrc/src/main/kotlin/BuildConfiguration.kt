import org.gradle.api.Project

@Suppress("KDocMissingDocumentation", "MemberVisibilityCanBePrivate")
object BuildConfiguration {
    /**
     * The version of the JVM to target by the Kotlin compiler.
     */
    fun jvmTarget(project: Project): String {
        return getProperty(project, "jvm.target") ?: "11"
    }

    /**
     * Should the build process download node-js if it is not present? (default: true)
     */
    fun downloadNodeJs(project: Project): Boolean {
        return getProperty(project, "nodejs.download") != "false"
    }

    /**
     * The Operating System the build is running on.
     */
    fun hostOs(project: Project): HostOs {
        return HostOs(getProperty(project, "os.name") ?: "Unknown")
    }

    /**
     * The headless web browser to run the JS tests on.
     */
    fun jsBrowser(project: Project): JsBrowser {
        return JsBrowser(getProperty(project, "js.browser"), hostOs(project))
    }

    /**
     * The Open Source Software Repository Hosting (OSSRH) username.
     */
    fun ossrhUsername(project: Project): String? {
        return getProperty(project, "ossrh.username", "OSSRH_USERNAME")
    }

    /**
     * The Open Source Software Repository Hosting (OSSRH) password.
     */
    fun ossrhPassword(project: Project): String? {
        return getProperty(project, "ossrh.password", "OSSRH_PASSWORD")
    }

    /**
     * Sign the Maven artifacts.
     */
    fun signMavenArtifacts(project: Project): Boolean {
        return when (project.gradle.taskGraph.hasTask("publish")) {
            true -> true
            else -> getProperty(project, "maven.sign") == "true"
        }
    }

    private fun getProperty(project: Project, name: String, envName: String? = null): String? {
        val projectValue = project.findProperty(name)?.toString()
        val systemValue = System.getProperty(name)
        val envValue = envName?.let { System.getenv(it) }
        return projectValue ?: systemValue ?: envValue
    }
}
