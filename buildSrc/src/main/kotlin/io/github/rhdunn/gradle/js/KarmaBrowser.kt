// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package io.github.rhdunn.gradle.js

import org.gradle.api.GradleException

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

    /**
     * Use Opera to run the tests.
     */
    Opera("Opera"),

    /**
     * Use Internet Explorer to run the tests.
     */
    Ie("Internet Explorer"),
}

/**
 * Returns the web browser used by the Karma test harness.
 */
fun KarmaBrowser(name: String?): KarmaBrowser? = when (name) {
    "chrome" -> KarmaBrowser.Chrome
    "chromium" -> KarmaBrowser.Chromium
    "firefox" -> KarmaBrowser.Firefox
    "phantom-js" -> KarmaBrowser.PhantomJs
    "safari" -> KarmaBrowser.Safari
    "opera" -> KarmaBrowser.Opera
    "ie" -> KarmaBrowser.Ie
    null -> null
    else -> throw GradleException("Invalid value for the 'karma.browser' property.")
}
