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

/**
 * The web browser used to run the Karma tests on.
 *
 * @param browserName The name of the headless web browser.
 * @param browser The browser type.
 * @param channel The development/release channel.
 */
sealed class KarmaBrowserTarget(
    val browser: KarmaBrowser,
    val channel: KarmaBrowserChannel = KarmaBrowserChannel.Release
) {
    /**
     * Use Chrome headless to run the tests.
     */
    object Chrome : KarmaBrowserTarget(KarmaBrowser.Chrome)

    /**
     * Use Chrome (Canary) headless to run the tests.
     */
    object ChromeCanary : KarmaBrowserTarget(KarmaBrowser.Chrome, KarmaBrowserChannel.Canary)

    /**
     * Use Chromium headless to run the tests.
     */
    object Chromium : KarmaBrowserTarget(KarmaBrowser.Chromium)

    /**
     * Use Firefox headless to run the tests.
     */
    object Firefox : KarmaBrowserTarget(KarmaBrowser.Firefox)

    /**
     * Use Firefox (Aurora) headless to run the tests.
     */
    object FirefoxAurora : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Aurora)

    /**
     * Use Firefox (Developer) headless to run the tests.
     */
    object FirefoxDeveloper : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Developer)

    /**
     * Use Firefox (Nightly) headless to run the tests.
     */
    object FirefoxNightly : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Nightly)

    /**
     * Use Phantom JS to run the tests.
     */
    object PhantomJs : KarmaBrowserTarget(KarmaBrowser.PhantomJs)

    /**
     * Use Safari to run the tests.
     */
    object Safari : KarmaBrowserTarget(KarmaBrowser.Safari)
}

/**
 * Returns the web browser used to run the Karma tests on.
 */
fun KarmaBrowserTarget(
    browser: KarmaBrowser,
    channel: KarmaBrowserChannel,
    headless: Boolean
): KarmaBrowserTarget = when (browser) {
    KarmaBrowser.Chrome -> when (channel) {
        KarmaBrowserChannel.Release -> KarmaBrowserTarget.Chrome
        KarmaBrowserChannel.Canary -> KarmaBrowserTarget.ChromeCanary
        else -> throw GradleException("Chrome does not support the 'karma.browser.channel' property value.")
    }

    KarmaBrowser.Chromium -> when (channel) {
        KarmaBrowserChannel.Release -> KarmaBrowserTarget.Chromium
        else -> throw GradleException("Chromium does not support the 'karma.browser.channel' property value.")
    }

    KarmaBrowser.Firefox -> when (channel) {
        KarmaBrowserChannel.Release -> KarmaBrowserTarget.Firefox
        KarmaBrowserChannel.Aurora -> KarmaBrowserTarget.FirefoxAurora
        KarmaBrowserChannel.Developer -> KarmaBrowserTarget.FirefoxDeveloper
        KarmaBrowserChannel.Nightly -> KarmaBrowserTarget.FirefoxNightly
        else -> throw GradleException("Firefox does not support the 'karma.browser.channel' property value.")
    }

    KarmaBrowser.PhantomJs -> when (channel) {
        KarmaBrowserChannel.Release -> KarmaBrowserTarget.PhantomJs
        else -> throw GradleException("Phantom JS does not support the 'karma.browser.channel' property value.")
    }

    KarmaBrowser.Safari -> when (channel) {
        KarmaBrowserChannel.Release -> KarmaBrowserTarget.Safari
        else -> throw GradleException("Safari does not support the 'karma.browser.channel' property value.")
    }
}
