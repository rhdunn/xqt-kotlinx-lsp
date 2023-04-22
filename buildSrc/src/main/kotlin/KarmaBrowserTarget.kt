// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
import org.jetbrains.kotlin.konan.target.HostManager

/**
 * The headless web browser used to test the Kotlin JS code.
 *
 * @param browserName The name of the headless web browser.
 */
sealed class KarmaBrowserTarget(val browserName: String) {
    /**
     * Use Chrome headless to run the tests.
     */
    object Chrome : KarmaBrowserTarget("Chrome")

    /**
     * Use Chrome (Canary) headless to run the tests.
     */
    object ChromeCanary : KarmaBrowserTarget("Chrome Canary")

    /**
     * Use Chromium headless to run the tests.
     */
    object Chromium : KarmaBrowserTarget("Chromium")

    /**
     * Use Firefox headless to run the tests.
     */
    object Firefox : KarmaBrowserTarget("Firefox")

    /**
     * Use Firefox (Aurora) headless to run the tests.
     */
    object FirefoxAurora : KarmaBrowserTarget("Firefox Aurora")

    /**
     * Use Firefox (Developer) headless to run the tests.
     */
    object FirefoxDeveloper : KarmaBrowserTarget("Firefox Developer")

    /**
     * Use Firefox (Nightly) headless to run the tests.
     */
    object FirefoxNightly : KarmaBrowserTarget("Firefox Nightly")

    /**
     * Use Phantom JS to run the tests.
     */
    object PhantomJs : KarmaBrowserTarget("Phantom JS")

    /**
     * Use Safari to run the tests.
     */
    object Safari : KarmaBrowserTarget("Safari")
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
