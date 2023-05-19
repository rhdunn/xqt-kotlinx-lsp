// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import io.github.rhdunn.gradle.maven.BuildType
import io.github.rhdunn.gradle.maven.SupportedVariants
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.konan.target.KonanTarget

/**
 * Information about the project.
 */
object ProjectMetadata {
    /**
     * A short description of the project.
     */
    const val Description = "Kotlin multiplatform Language Server Protocol (LSP) library"

    /**
     * Version information about the current build of the project.
     */
    object Build {
        /**
         * The semantic version of the current version.
         */
        const val VersionTag = "3.0.0"

        /**
         * The build type of this project.
         */
        val Type = BuildType.Snapshot

        /**
         * The artifact version ID.
         */
        val Version = "$VersionTag${Type.suffix}"
    }

    /**
     * Information about which platform targets to build for.
     */
    object BuildTargets {
        /**
         * The default value for the `jvm.variants` property.
         */
        val DefaultJvmVariants = SupportedVariants.All

        /**
         * The default value for the `jvm.variants` property.
         */
        const val DefaultJvmTarget = "11"

        /**
         * Specifies which Java versions to target in Kotlin/JVM.
         */
        val JvmTargets: List<JavaVersion> = listOf(
            JavaVersion.VERSION_11, // LTS
            JavaVersion.VERSION_17, // LTS
        )

        /**
         * The default value for the `konan.variants` property.
         */
        val DefaultKonanVariants = SupportedVariants.All

        /**
         * Specifies which Java versions to target in Kotlin/JVM.
         *
         * @see <a href="https://kotlinlang.org/docs/native-target-support.html">Kotlin/Native target support</a>
         */
        val KonanTargets: List<KonanTarget> = listOf(
            KonanTarget.ANDROID_ARM32, // Tier 3
            KonanTarget.ANDROID_ARM64, // Tier 3
            KonanTarget.ANDROID_X64, // Tier 3
            KonanTarget.ANDROID_X86, // Tier 3
            KonanTarget.IOS_ARM64, // Tier 2
            KonanTarget.IOS_SIMULATOR_ARM64, // Tier 1
            KonanTarget.IOS_X64, // Tier 1
            KonanTarget.LINUX_ARM64, // Tier 2
            KonanTarget.LINUX_X64, // Tier 1 ; native host
            KonanTarget.MACOS_ARM64, // Tier 1 ; native host
            KonanTarget.MACOS_X64, // Tier 1 ; native host
            KonanTarget.MINGW_X64, // Tier 3 ; native host
            KonanTarget.TVOS_ARM64, // Tier 2
            KonanTarget.TVOS_SIMULATOR_ARM64, // Tier 2
            KonanTarget.TVOS_X64, // Tier 2
            KonanTarget.WATCHOS_ARM32, // Tier 2
            KonanTarget.WATCHOS_ARM64, // Tier 2
            KonanTarget.WATCHOS_DEVICE_ARM64, // Tier 3
            KonanTarget.WATCHOS_SIMULATOR_ARM64, // Tier 2
            KonanTarget.WATCHOS_X64, // Tier 2
        )
    }

    /**
     * Information about the GitHub account that owns the project.
     */
    object GitHub {
        /**
         * The ID of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountId = "rhdunn"

        /**
         * The name of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountName = "Reece H. Dunn"

        /**
         * The email address of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountEmail = "msclrhd@gmail.com"

        /**
         * The ID of the GitHub repository.
         */
        const val RepoId = "xqt-kotlinx-lsp"

        /**
         * The Maven group ID.
         */
        const val GroupId = "io.github.$AccountId"

        /**
         * The URL of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountUrl = "https://github.com/$AccountId"

        /**
         * The URL to the GitHub repository.
         */
        const val ProjectUrl = "https://github.com/$AccountId/$RepoId"

        /**
         * The URL to clone the repository via SSH.
         */
        const val CloneSshUrl = "git@github.com:$AccountId/$RepoId.git"

        /**
         * The URL to the GitHub repository issues.
         */
        const val IssuesUrl = "https://github.com/$AccountId/$RepoId/issues"
    }

    /**
     * Information about the project license as it appears in the Maven POM file.
     */
    object License {
        /**
         * The name of the license, for Maven POM metadata.
         */
        const val Name = "The Apache Software License, Version 2.0"

        /**
         * The URL where the license text can be found.
         */
        const val Url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
    }

    /**
     * Information about the project copyright.
     */
    object Copyright {
        /**
         * The copyright year range for the project.
         */
        const val Year = "2022-2023"

        /**
         * The person or organization owning the copyright for the project.
         */
        const val Owner = "Reece H. Dunn"
    }
}
