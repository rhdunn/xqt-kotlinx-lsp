allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.dokka") version Version.Plugin.Dokka
}
