// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0

/**
 * Information about the project.
 */
object ProjectMetadata {
    /**
     * A short description of the project.
     */
    const val Description = "Kotlin multiplatform project template" // TODO: Modify this property.

    /**
     * Version information about the current build of the project.
     */
    object Build {
        /**
         * The semantic version of the current version.
         */
        const val VersionTag = "1.0.0" // TODO: Modify this property.

        /**
         * The build type of this project.
         */
        val Type = BuildType.Snapshot // TODO: Modify this property.

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
        const val AccountId = "rhdunn" // TODO: Modify this property.

        /**
         * The name of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountName = "Reece H. Dunn" // TODO: Modify this property.

        /**
         * The email address of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountEmail = "msclrhd@gmail.com" // TODO: Modify this property.

        /**
         * The ID of the GitHub repository.
         */
        const val RepoId = "kotlin-multiplatform-template" // TODO: Modify this property.

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
        const val Name = "The Apache Software License, Version 2.0" // TODO: Modify this property.

        /**
         * The URL where the license text can be found.
         */
        const val Url = "https://www.apache.org/licenses/LICENSE-2.0.txt" // TODO: Modify this property.
    }

    /**
     * Information about the project copyright.
     */
    object Copyright {
        /**
         * The copyright year range for the project.
         */
        const val Year = "2023" // TODO: Modify this property.

        /**
         * The person or organization owning the copyright for the project.
         */
        const val Owner = "Reece H. Dunn" // TODO: Modify this property.
    }
}
