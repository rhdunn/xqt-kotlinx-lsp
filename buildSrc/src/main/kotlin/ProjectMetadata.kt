// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

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
        const val VersionTag = "1.0.1"

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
