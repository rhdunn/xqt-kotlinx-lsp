plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }

    js(BOTH) {
        browser {
        }

        nodejs {
        }
    }

    val hostOs = System.getProperty("os.name")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("commonMain")
        }

        val jvmMain by getting {
            kotlin.srcDir("jvmMain")
        }

        val jsMain by getting {
            kotlin.srcDir("jsMain")
        }

        val nativeMain by getting {
            kotlin.srcDir("nativeMain")
        }
    }
}
