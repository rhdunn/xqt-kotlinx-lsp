@Suppress("KDocMissingDocumentation", "MemberVisibilityCanBePrivate")
object ProjectMetadata {
    object Build {
        /**
         * The semantic version of the current version.
         */
        const val VersionTag = "0.1"

        /**
         * The build type of this project.
         */
        val Type = BuildType.Release

        /**
         * The artifact version ID.
         */
        val Version = "$VersionTag${Type.suffix}"
    }

    object GitHub {
        /**
         * The ID of the GitHub account (organization or developer) to which this project belongs.
         */
        const val AccountId = "rhdunn"

        /**
         * The Maven group ID.
         */
        const val GroupId = "io.github.$AccountId"
    }

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
