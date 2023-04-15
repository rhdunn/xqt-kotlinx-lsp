@file:Suppress("KDocMissingDocumentation")

enum class JsBrowser(val browserName: String) {
    Chrome("Chrome"),
    ChromeCanary("Chrome Canary"),
    Chromium("Chromium"),
    Firefox("Firefox"),
    FirefoxAurora("Firefox Aurora"),
    FirefoxDeveloper("Firefox Developer"),
    FirefoxNightly("Firefox Nightly"),
    PhantomJs("Phantom JS"),
    Safari("Safari")
}

fun JsBrowser(browserName: String?, hostOs: HostOs): JsBrowser = when (browserName) {
    JsBrowser.Chrome.browserName -> JsBrowser.Chrome
    JsBrowser.ChromeCanary.browserName -> JsBrowser.ChromeCanary
    JsBrowser.Chromium.browserName -> JsBrowser.Chromium
    JsBrowser.Firefox.browserName -> JsBrowser.Firefox
    JsBrowser.FirefoxAurora.browserName -> JsBrowser.FirefoxAurora
    JsBrowser.FirefoxDeveloper.browserName -> JsBrowser.FirefoxDeveloper
    JsBrowser.FirefoxNightly.browserName -> JsBrowser.FirefoxNightly
    JsBrowser.PhantomJs.browserName -> JsBrowser.PhantomJs
    JsBrowser.Safari.browserName -> JsBrowser.Safari
    else -> when (hostOs) {
        HostOs.MacOsX -> JsBrowser.Safari
        else -> JsBrowser.Firefox
    }
}
