// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.jetbrains.kotlin.konan.target.HostManager

/**
 * The headless web browser used to test the Kotlin JS code.
 *
 * @param browserName The name of the headless web browser.
 */
enum class JsBrowser(val browserName: String) {
    /**
     * Use Chrome headless to run the tests.
     */
    Chrome("Chrome"),
    /**
     * Use Chrome (Canary) headless to run the tests.
     */
    ChromeCanary("Chrome Canary"),
    /**
     * Use Chromium headless to run the tests.
     */
    Chromium("Chromium"),
    /**
     * Use Firefox headless to run the tests.
     */
    Firefox("Firefox"),
    /**
     * Use Firefox (Aurora) headless to run the tests.
     */
    FirefoxAurora("Firefox Aurora"),
    /**
     * Use Firefox (Developer) headless to run the tests.
     */
    FirefoxDeveloper("Firefox Developer"),
    /**
     * Use Firefox (Nightly) headless to run the tests.
     */
    FirefoxNightly("Firefox Nightly"),
    /**
     * Use Phantom JS to run the tests.
     */
    PhantomJs("Phantom JS"),
    /**
     * Use Safari to run the tests.
     */
    Safari("Safari")
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
