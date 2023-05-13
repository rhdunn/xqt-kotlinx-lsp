// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.js

import org.gradle.api.GradleException

/**
 * The web browser development/release channel.
 */
enum class KarmaBrowserChannel {
    /**
     * The official production releases for the browser.
     */
    Release,

    /**
     * The Google Chrome "Canary" builds.
     */
    Canary,

    /**
     * The Mozilla Firefox "Aurora" builds.
     */
    Aurora,

    /**
     * The Mozilla Firefox "Developer" builds.
     */
    Developer,

    /**
     * The Mozilla Firefox "Nightly" builds.
     */
    Nightly,
}

/**
 * Returns the web browser development/release channel.
 */
fun KarmaBrowserChannel(channel: String): KarmaBrowserChannel = when (channel) {
    "release" -> KarmaBrowserChannel.Release
    "canary" -> KarmaBrowserChannel.Canary
    "aurora" -> KarmaBrowserChannel.Aurora
    "developer" -> KarmaBrowserChannel.Developer
    "nightly" -> KarmaBrowserChannel.Nightly
    else -> throw GradleException("Invalid value for the 'karma.browser.channel' property.")
}
