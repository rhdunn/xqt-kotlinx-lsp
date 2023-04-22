// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.jetbrains.kotlin.konan.target.HostManager

/**
 * The headless web browser used to test the Kotlin JS code.
 *
 * @param browserName The name of the headless web browser.
 */
sealed class JsBrowser(val browserName: String) {
    /**
     * Use Chrome headless to run the tests.
     */
    object Chrome : JsBrowser("Chrome")

    /**
     * Use Chrome (Canary) headless to run the tests.
     */
    object ChromeCanary : JsBrowser("Chrome Canary")

    /**
     * Use Chromium headless to run the tests.
     */
    object Chromium : JsBrowser("Chromium")

    /**
     * Use Firefox headless to run the tests.
     */
    object Firefox : JsBrowser("Firefox")

    /**
     * Use Firefox (Aurora) headless to run the tests.
     */
    object FirefoxAurora : JsBrowser("Firefox Aurora")

    /**
     * Use Firefox (Developer) headless to run the tests.
     */
    object FirefoxDeveloper : JsBrowser("Firefox Developer")

    /**
     * Use Firefox (Nightly) headless to run the tests.
     */
    object FirefoxNightly : JsBrowser("Firefox Nightly")

    /**
     * Use Phantom JS to run the tests.
     */
    object PhantomJs : JsBrowser("Phantom JS")

    /**
     * Use Safari to run the tests.
     */
    object Safari : JsBrowser("Safari")
}

/**
 * The headless web browser used to test the Kotlin JS code.
 *
 * If `browserName` is `null` or unrecognised, a default browser is selected.
 *
 * @param browserName The name of the headless web browser.
 */
fun JsBrowser(browserName: String?): JsBrowser = when (browserName) {
    JsBrowser.Chrome.browserName -> JsBrowser.Chrome
    JsBrowser.ChromeCanary.browserName -> JsBrowser.ChromeCanary
    JsBrowser.Chromium.browserName -> JsBrowser.Chromium
    JsBrowser.Firefox.browserName -> JsBrowser.Firefox
    JsBrowser.FirefoxAurora.browserName -> JsBrowser.FirefoxAurora
    JsBrowser.FirefoxDeveloper.browserName -> JsBrowser.FirefoxDeveloper
    JsBrowser.FirefoxNightly.browserName -> JsBrowser.FirefoxNightly
    JsBrowser.PhantomJs.browserName -> JsBrowser.PhantomJs
    JsBrowser.Safari.browserName -> JsBrowser.Safari
    else -> when {
        HostManager.hostIsMac -> JsBrowser.Safari
        else -> JsBrowser.Firefox
    }
}
