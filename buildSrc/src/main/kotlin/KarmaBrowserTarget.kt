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
 * The headless web browser used to test the Kotlin JS code.
 *
 * @param browserName The name of the headless web browser.
 * @param browser The browser type.
 */
sealed class KarmaBrowserTarget(val browserName: String, val browser: KarmaBrowser) {
    /**
     * Use Chrome headless to run the tests.
     */
    object Chrome : KarmaBrowserTarget("Chrome", KarmaBrowser.Chrome)

    /**
     * Use Chrome (Canary) headless to run the tests.
     */
    object ChromeCanary : KarmaBrowserTarget("Chrome Canary", KarmaBrowser.Chrome)

    /**
     * Use Chromium headless to run the tests.
     */
    object Chromium : KarmaBrowserTarget("Chromium", KarmaBrowser.Chromium)

    /**
     * Use Firefox headless to run the tests.
     */
    object Firefox : KarmaBrowserTarget("Firefox", KarmaBrowser.Firefox)

    /**
     * Use Firefox (Aurora) headless to run the tests.
     */
    object FirefoxAurora : KarmaBrowserTarget("Firefox Aurora", KarmaBrowser.Firefox)

    /**
     * Use Firefox (Developer) headless to run the tests.
     */
    object FirefoxDeveloper : KarmaBrowserTarget("Firefox Developer", KarmaBrowser.Firefox)

    /**
     * Use Firefox (Nightly) headless to run the tests.
     */
    object FirefoxNightly : KarmaBrowserTarget("Firefox Nightly", KarmaBrowser.Firefox)

    /**
     * Use Phantom JS to run the tests.
     */
    object PhantomJs : KarmaBrowserTarget("Phantom JS", KarmaBrowser.PhantomJs)

    /**
     * Use Safari to run the tests.
     */
    object Safari : KarmaBrowserTarget("Safari", KarmaBrowser.Safari)
}

/**
 * The headless web browser used to test the Kotlin JS code.
 *
 * If `browserName` is `null` or unrecognised, a default browser is selected.
 *
 * @param browserName The name of the headless web browser.
 */
fun KarmaBrowserTarget(browserName: String?): KarmaBrowserTarget = when (browserName) {
    KarmaBrowserTarget.Chrome.browserName -> KarmaBrowserTarget.Chrome
    KarmaBrowserTarget.ChromeCanary.browserName -> KarmaBrowserTarget.ChromeCanary
    KarmaBrowserTarget.Chromium.browserName -> KarmaBrowserTarget.Chromium
    KarmaBrowserTarget.Firefox.browserName -> KarmaBrowserTarget.Firefox
    KarmaBrowserTarget.FirefoxAurora.browserName -> KarmaBrowserTarget.FirefoxAurora
    KarmaBrowserTarget.FirefoxDeveloper.browserName -> KarmaBrowserTarget.FirefoxDeveloper
    KarmaBrowserTarget.FirefoxNightly.browserName -> KarmaBrowserTarget.FirefoxNightly
    KarmaBrowserTarget.PhantomJs.browserName -> KarmaBrowserTarget.PhantomJs
    KarmaBrowserTarget.Safari.browserName -> KarmaBrowserTarget.Safari
    else -> when {
        HostManager.hostIsMac -> KarmaBrowserTarget.Safari
        else -> KarmaBrowserTarget.Firefox
    }
}
