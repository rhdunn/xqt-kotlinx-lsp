// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.gradle.api.GradleException
import org.jetbrains.kotlin.konan.target.HostManager

/**
 * The web browser used by the Karma test harness.
 *
 * @param displayName The web browser display name.
 */
enum class KarmaBrowser(val displayName: String) {
    /**
     * Use Google Chrome to run the tests.
     */
    Chrome("Chrome"),

    /**
     * Use Google Chromium to run the tests.
     */
    Chromium("Chromium"),

    /**
     * Use Mozilla Firefox to run the tests.
     */
    Firefox("Firefox"),

    /**
     * Use Phantom JS to run the tests.
     */
    PhantomJs("Phantom JS"),

    /**
     * Use Safari to run the tests.
     */
    Safari("Safari"),
}

/**
 * Returns the web browser used by the Karma test harness.
 */
fun KarmaBrowser(name: String?): KarmaBrowser = when (name) {
    "chrome" -> KarmaBrowser.Chrome
    "chromium" -> KarmaBrowser.Chromium
    "firefox" -> KarmaBrowser.Firefox
    "phantom-js" -> KarmaBrowser.PhantomJs
    "safari" -> KarmaBrowser.Safari
    null -> when {
        HostManager.hostIsMac -> KarmaBrowser.Safari
        else -> KarmaBrowser.Firefox
    }

    else -> throw GradleException("Invalid value for the 'karma.browser' property.")
}

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

private typealias Browser = KarmaBrowser
private typealias Channel = KarmaBrowserChannel

/**
 * The web browser used to run the Karma tests on.
 *
 * @param browser The browser type.
 * @param channel The development/release channel.
 */
@Suppress("KDocMissingDocumentation", "IncorrectFormatting")
sealed class KarmaBrowserTarget(
    val browser: KarmaBrowser,
    val channel: KarmaBrowserChannel = KarmaBrowserChannel.Release
) {
    object Chrome           : KarmaBrowserTarget(Browser.Chrome,    Channel.Release)
    object ChromeCanary     : KarmaBrowserTarget(Browser.Chrome,    Channel.Canary)
    object Chromium         : KarmaBrowserTarget(Browser.Chromium,  Channel.Release)
    object Firefox          : KarmaBrowserTarget(Browser.Firefox,   Channel.Release)
    object FirefoxAurora    : KarmaBrowserTarget(Browser.Firefox,   Channel.Aurora)
    object FirefoxDeveloper : KarmaBrowserTarget(Browser.Firefox,   Channel.Developer)
    object FirefoxNightly   : KarmaBrowserTarget(Browser.Firefox,   Channel.Nightly)
    object PhantomJs        : KarmaBrowserTarget(Browser.PhantomJs, Channel.Release)
    object Safari           : KarmaBrowserTarget(Browser.Safari,    Channel.Release)

    companion object {
        val targets: List<KarmaBrowserTarget> = listOf(
            Chrome, ChromeCanary,
            Chromium,
            Firefox, FirefoxAurora, FirefoxDeveloper, FirefoxNightly,
            PhantomJs,
            Safari
        )
    }
}

/**
 * Returns the web browser used to run the Karma tests on.
 */
fun KarmaBrowserTarget(
    browser: KarmaBrowser,
    channel: KarmaBrowserChannel,
    headless: Boolean
): KarmaBrowserTarget {
    KarmaBrowserTarget.targets.forEach {
        if (it.browser == browser && it.channel == channel) {
            return it
        }
    }
    throw GradleException("Unknown KarmaBrowserTarget for the specified karma.browser configuration.")
}
