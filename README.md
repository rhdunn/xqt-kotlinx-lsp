# kotlin-multiplatform-template
> A template project for Kotlin multiplatform projects

The `kotlin-multiplatform-template` project is an open-source template that
provides common infrastructure for creating and deploying Kotlin multiplatform
projects.

> NOTE: This readme should be edited to describe your project, removing the
> text describing the content of this template.

## IntelliJ IDE
This template adds `.gitignore` rules for the IntelliJ `.idea` folder to keep
the project clean.
> NOTE: If you want to share IntelliJ settings, simply remove the "IntelliJ IDE
> Settings" section of the `.gitignore` file.

## Gradle
This template adds `.gitignore` rules to ignore the gradle build output.

This template provides a base `build.gradle.kts` file for common settings
across subprojects and for the `buildSrc` directory. These should not need
modifying.

This template provides a base `settings.gradle.kts` file.
> NOTE: This should be modified to set the project name and to include any
> subprojects.

### Gradle Wrapper
This template adds `.gitignore` rules to ignore the gradle wrapper binaries to
prevent the binaries from being included in the git repository.
> NOTE: If you want to add those binaries to the repository, simply remove the
> "Gradle Wrapper" section of the `.gitignore` file.

## Kotlin Multiplatform
This template defines common gradle configuration options in the
`gradle.properties` file for Kotlin multiplatform projects. These settings
should not need modifying.

### Kotlin JVM
This template provides a GitHub Actions workflow (`build-jvm`) for building the
project with Kotlin/JVM.

#### jvm.target
To configure this setting, add the following to the `build.gradle.kts` file:
```
kotlin.jvm {
    compilations.all {
        kotlinOptions.jvmTarget = BuildConfiguration.jvmTarget(project)
    }
}
```

### Kotlin JS
This template provides GitHub Actions workflows for building the project with
Kotlin/JS. It supports the new IR compiler (`build-js-ir`) and the legacy
compiler (`build-js-legacy`).

#### Karma Browser Tests
To configure this setting, add the following to the `build.gradle.kts` file:
```
kotlin.js(KotlinJsCompilerType.BOTH).browser {
    testTask {
        useKarma {
            when (BuildConfiguration.karmaBrowserTarget(project)) {
                KarmaBrowserTarget.Chrome -> useChrome()
                KarmaBrowserTarget.ChromeHeadless -> useChromeHeadless()
                KarmaBrowserTarget.ChromeCanary -> useChromeCanary()
                KarmaBrowserTarget.ChromeCanaryHeadless -> useChromeCanaryHeadless()
                KarmaBrowserTarget.Chromium -> useChromium()
                KarmaBrowserTarget.ChromiumHeadless -> useChromiumHeadless()
                KarmaBrowserTarget.Firefox -> useFirefox()
                KarmaBrowserTarget.FirefoxHeadless -> useFirefoxHeadless()
                KarmaBrowserTarget.FirefoxAurora -> useFirefoxAurora()
                KarmaBrowserTarget.FirefoxAuroraHeadless -> useFirefoxAuroraHeadless()
                KarmaBrowserTarget.FirefoxDeveloper -> useFirefoxDeveloper()
                KarmaBrowserTarget.FirefoxDeveloperHeadless -> useFirefoxDeveloperHeadless()
                KarmaBrowserTarget.FirefoxNightly -> useFirefoxNightly()
                KarmaBrowserTarget.FirefoxNightlyHeadless -> useFirefoxNightlyHeadless()
                KarmaBrowserTarget.PhantomJs -> usePhantomJS()
                KarmaBrowserTarget.Safari -> useSafari()
                KarmaBrowserTarget.Opera -> useOpera()
                KarmaBrowserTarget.Ie -> useIe()
            }
        }
    }
}
```

#### nodejs.download
To configure this setting, add the following to the `build.gradle.kts` file:
```
rootProject.plugins.withType<NodeJsRootPlugin> {
    val download = BuildConfiguration.nodeJsDownload(project)
    rootProject.the<NodeJsRootExtension>().download = download
}
```

### Kotlin Native
This template provides a GitHub Actions workflow (`build-native`) for building
the project with Kotlin/Native on Windows, Linux, and Mac OS X.

## Configuration Properties
The following configuration properties are available to configure the build:

### jvm.target
The `jvm.target` build property configures the version of the Java VM to target
by the Kotlin compiler. This is used by the GitHub Actions to build against the
LTS releases of JVM, 11 and 17.

### karma.browser
The `karma.browser` build property configures the name of the browser to use in
the Kotlin/JS tests when run on the browser.

### karma.browser.channel
The `karma.browser.channel` build property configures the name of the
development/release channel of the browser used to run the Kotlin/JS tests.

### karma.browser.headless
The `karma.browser.headless` build property determines whether the web browser
runs in headless mode, or with a visible graphical interface.

### nodejs.download
The `nodejs.download` build property configures whether the build should
download node when building and running the Kotlin/JS targets. If this is false
the build will use the system's node installation. This is used by the GitHub
Actions to prevent node being downloaded during the build.

### ossrh.username
The Open Source Software Repository Hosting (OSSRH) username to use when
publishing artifacts to Maven Central. This takes precedence over the
`OSSRH_USERNAME` environment variable.

### ossrh.password
The Open Source Software Repository Hosting (OSSRH) password to use when
publishing artifacts to Maven Central. This takes precedence over the
`OSSRH_PASSWORD` environment variable.

## Environment Variables
The following environment variables are available to configure the build:

### ossrh.username
The Open Source Software Repository Hosting (OSSRH) username to use when
publishing artifacts to Maven Central. This takes precedence over the
`OSSRH_USERNAME` environment variable.

### ossrh.password
The Open Source Software Repository Hosting (OSSRH) password to use when
publishing artifacts to Maven Central. This takes precedence over the
`OSSRH_PASSWORD` environment variable.

## License
Copyright (C) 2023 Reece H. Dunn

SPDX-License-Identifier: [Apache-2.0](LICENSE)
