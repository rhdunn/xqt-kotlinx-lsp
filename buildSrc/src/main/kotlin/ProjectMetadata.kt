@Suppress("KDocMissingDocumentation", "MemberVisibilityCanBePrivate")
object ProjectMetadata {
    object Build {
        /**
         * The semantic version of the current version.
         */
        const val VersionTag = "0.1"
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
}
