// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import io.github.rhdunn.gradle.js.KarmaBrowser
import io.github.rhdunn.gradle.js.KarmaBrowserChannel
import io.github.rhdunn.gradle.js.KarmaBrowserTarget
import io.github.rhdunn.gradle.maven.ArtifactSigningMethod
import io.github.rhdunn.gradle.maven.MavenSonatype
import io.github.rhdunn.gradle.maven.SupportedVariants
import org.gradle.api.GradleException
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

/**
 * Accessors for the build configuration options.
 */
@Suppress("MemberVisibilityCanBePrivate")
object BuildConfiguration {
    /**
     * The version of the Java Virtual Machine (JVM) to target by the Kotlin compiler.
     */
    fun jvmTarget(project: Project): String {
        val target = getProperty(project, "jvm.target") ?: ProjectMetadata.BuildTargets.DefaultJvmTarget
        if (JavaVersion.toVersion(target) !in ProjectMetadata.BuildTargets.JvmTargets)
            throw GradleException("The specified jvm.target is not in the configured project metadata.")
        return target
    }

    /**
     * The variants of the Java Virtual Machine (JVM) to support.
     */
    fun jvmVariants(project: Project): SupportedVariants {
        return when (ProjectMetadata.BuildTargets.JvmTargets.size) {
            0 -> SupportedVariants.None // No JVM targets configured.
            else -> when (getProperty(project, "jvm.variants")) {
                "all" -> SupportedVariants.All
                "target-only" -> SupportedVariants.TargetOnly
                "none" -> SupportedVariants.None
                null -> ProjectMetadata.BuildTargets.DefaultJvmVariants
                else -> throw GradleException("Invalid value for the 'jvm.variants' property.")
            }
        }
    }

    /**
     * The JVM target as a JavaVersion instance.
     */
    fun javaVersion(project: Project): JavaVersion {
        return JavaVersion.toVersion(jvmTarget(project))
    }

    /**
     * The JVM target as a JavaLanguageVersion instance.
     */
    fun javaLanguageVersion(project: Project): JavaLanguageVersion {
        return JavaLanguageVersion.of(javaVersion(project).majorVersion)
    }

    /**
     * The web browser used by the Karma test harness.
     */
    fun karmaBrowser(project: Project): KarmaBrowser? {
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
        return when (browser) {
            null -> when {
                HostManager.hostIsMac -> KarmaBrowserTarget.Safari
                else -> KarmaBrowserTarget.FirefoxHeadless
            }

            else -> KarmaBrowserTarget.valueOf(browser, channel, headless = headless)
        }
    }

    /**
     * The Kotlin/Native platform to target.
     */
    fun konanTarget(project: Project): KonanTarget {
        val target = getProperty(project, "konan.target")
        val konanTarget = target?.let { KonanTarget.predefinedTargets[it] } ?: HostManager.host
        if (konanTarget !in ProjectMetadata.BuildTargets.KonanTargets)
            throw GradleException("The specified konan.target is not in the configured project metadata.")
        return konanTarget
    }

    /**
     * The Kotlin/Native targets to support as native variants.
     */
    fun konanVariants(project: Project): SupportedVariants {
        return when (ProjectMetadata.BuildTargets.KonanTargets.size) {
            0 -> SupportedVariants.None // No Kotlin/Native targets configured.
            else -> when (getProperty(project, "konan.variants")) {
                "all" -> SupportedVariants.All
                "target-only" -> SupportedVariants.TargetOnly
                "none" -> SupportedVariants.None
                null -> ProjectMetadata.BuildTargets.DefaultKonanVariants
                else -> throw GradleException("Invalid value for the 'konan.variants' property.")
            }
        }
    }

    /**
     * The repository to publish Maven Central (Sonatype) artifacts to.
     */
    fun mavenRepositorySonatype(project: Project): MavenSonatype {
        return when (getProperty(project, "maven.repository.sonatype")) {
            "snapshot" -> MavenSonatype.Snapshot
            "release" -> MavenSonatype.Release
            "none", null -> MavenSonatype.None
            else -> throw GradleException("Invalid value for the 'maven.repository.sonatype' property.")
        }
    }

    /**
     * Sign the Maven artifacts.
     */
    fun mavenSignArtifacts(project: Project): ArtifactSigningMethod {
        return when (getProperty(project, "maven.sign")) {
            "env" -> ArtifactSigningMethod.Environment
            "gpg", "true" -> ArtifactSigningMethod.GpgCommand
            "none", "false", null -> ArtifactSigningMethod.None
            else -> throw GradleException("Invalid value for the 'maven.sign' property.")
        }
    }

    /**
     * The key id to use when using a subkey to sign the artifacts.
     */
    fun mavenSigningKeyId(project: Project): String? {
        return getProperty(project, "maven.sign.key.id", "SIGNING_KEY_ID")
    }

    /**
     * The ascii-armoured PGP private key to sign the artifacts with. Newlines are represented as `\n`.
     */
    fun mavenSigningKeyPrivate(project: Project): String? {
        return getProperty(project, "maven.sign.key.private", "SIGNING_KEY_PRIVATE")
            ?.replace("\\n", "\n")
    }

    /**
     * The password or passphrase for the private key.
     */
    fun mavenSigningKeyPassword(project: Project): String? {
        return getProperty(project, "maven.sign.key.password", "SIGNING_KEY_PASSWORD")
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
            ?.takeIf { value -> value.isNotBlank() }
        val systemValue = System.getProperty(name)
            ?.takeIf { value -> value.isNotBlank() }
        val envValue = envName?.let { System.getenv(it) }
            ?.takeIf { value -> value.isNotBlank() }
        return projectValue ?: systemValue ?: envValue
    }
}
