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
}
