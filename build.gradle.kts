allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version Version.Plugin.KotlinMultiplatform
    id("org.jetbrains.dokka") version Version.Plugin.Dokka
}

// Workaround for generating documentation. The root project needs kotlin to be
// configured for at least one multiplatform target, otherwise dokka will report
// "Nothing to document" in the dokkaHtml and dokkaHtmlPartial tasks.
kotlin {
    jvm()
}
