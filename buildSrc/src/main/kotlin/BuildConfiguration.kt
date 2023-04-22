// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.Project

/**
 * Accessors for the build configuration options.
 */
object BuildConfiguration {
    /**
     * The headless web browser to run the JS tests on.
     */
    fun jsBrowser(project: Project): JsBrowser {
        return JsBrowser(getProperty(project, "js.browser"))
    }

    private fun getProperty(project: Project, name: String, envName: String? = null): String? {
        val projectValue = project.findProperty(name)?.toString()
        val systemValue = System.getProperty(name)
        val envValue = envName?.let { System.getenv(it) }
        return projectValue ?: systemValue ?: envValue
    }
}
