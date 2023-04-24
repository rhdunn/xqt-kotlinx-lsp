// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * Accessors for the build configuration options.
 */
@Suppress("MemberVisibilityCanBePrivate")
object BuildConfiguration {
    /**
     * The version of the Java Virtual Machine (JVM) to target by the Kotlin compiler.
     */
    fun jvmTarget(project: Project): String {
        return getProperty(project, "jvm.target") ?: "11"
    }

    /**
     * The web browser used by the Karma test harness.
     */
    fun karmaBrowser(project: Project): KarmaBrowser {
        return KarmaBrowser(getProperty(project, "karma.browser"))
    }

    /**
     * The web browser development/release channel used by the Karma test harness.
     */
    fun karmaBrowserChannel(project: Project): KarmaBrowserChannel {
        return KarmaBrowserChannel(getProperty(project, "karma.browser.channel") ?: "release")
    }

    /**
     * Should the web browser used by the Karma test harness be run in headless mode?
     */
    fun karmaBrowserHeadless(project: Project): Boolean {
        return when (getProperty(project, "karma.browser.headless")) {
            "true", null -> true
            "false" -> false
            else -> throw GradleException("Invalid value for the 'karma.browser.headless' property.")
        }
    }

    /**
     * The web browser used to run the Karma tests on.
     */
    fun karmaBrowserTarget(project: Project): KarmaBrowserTarget {
        val browser = karmaBrowser(project)
        val channel = karmaBrowserChannel(project)
        val headless = karmaBrowserHeadless(project)
        return KarmaBrowserTarget.valueOf(browser, channel, headless = headless)
    }

    /**
     * Should the build process download node if it is not present?
     */
    fun nodeJsDownload(project: Project): Boolean {
        return when (getProperty(project, "nodejs.download")) {
            "true", null -> true
            "false" -> false
            else -> throw GradleException("Invalid value for the 'nodejs.download' property.")
        }
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

    private fun getProperty(project: Project, name: String, envName: String? = null): String? {
        val projectValue = project.findProperty(name)?.toString()
        val systemValue = System.getProperty(name)
        val envValue = envName?.let { System.getenv(it) }
        return projectValue ?: systemValue ?: envValue
    }
}
