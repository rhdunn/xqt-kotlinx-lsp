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

This template provides a template
[build.gradle.kts](buildSrc/template/build.gradle.kts) file that can be copied
into your subprojects to use as the starting point for the build script. This
provides support for:
1. Configuring the necessary plugins.
2. Setting the group ID and version for the package from the project metadata.
3. Configuration for Kotlin JS, JVM, and Native.
4. Configuration for Dokka documentation.
5. Configuration for Maven POM files, singing, and publishing.

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

## Supported Kotlin/Native Targets
| Target [1]                | Family       | Tier [1]       | Status         |
|---------------------------|--------------|----------------|----------------|
| `android_arm32`           | Android      | 3              | Build Only [3] |
| `android_arm64`           | Android      | 3              | Build Only [3] |
| `android_x64`             | Android      | 3              | Build Only [3] |
| `android_x86`             | Android      | 3              | Build Only [3] |
| `ios_arm32`               | Mac iOS      | Deprecated [2] | Build Only [3] |
| `ios_arm64`               | Mac iOS      | 2              | Build Only [3] |
| `ios_simulator_arm64`     | Mac iOS      | 1              | Build and Test |
| `ios_x64`                 | Mac iOS      | 1              | Build and Test |
| `linux_arm32_hfp`         | Linux        | Deprecated [2] | Build Only [3] |
| `linux_arm64`             | Linux        | 2              | Build Only [3] |
| `linux_mips32`            | Linux        | Deprecated [2] | Build Only [3] |
| `linux_mipsel32`          | Linux        | Deprecated [2] | Build Only [3] |
| `linux_x64`               | Linux        | 1 (Host)       | Build and Test |
| `macos_arm64`             | Mac OSX      | 1 (Host)       | Build and Test |
| `macos_x64`               | Mac OSX      | 1 (Host)       | Build and Test |
| `mingw_x64`               | MinGW        | 3 (Host)       | Build and Test |
| `mingw_x86`               | MinGW        | Deprecated [2] | Build Only [3] |
| `tvos_arm64`              | Mac TV OS    | 2              | Build Only [3] |
| `tvos_simulator_arm64`    | Mac TV OS    | 2              | Build and Test |
| `tvos_x64`                | Mac TV OS    | 2              | Build and Test |
| `wasm32`                  | WASM         | Deprecated [2] | Build Only [3] |
| `watchos_arm32`           | Mac Watch OS | 2              | Build Only [3] |
| `watchos_arm64`           | Mac Watch OS | 2              | Build Only [3] |
| `watchos_simulator_arm64` | Mac Watch OS | 2              | Build and Test |
| `watchos_x64`             | Mac Watch OS | 2              | Build Only [4] |
| `watchos_x86`             | Mac Watch OS | Deprecated [2] | Build and Test |

[1] See https://kotlinlang.org/docs/native-target-support.html for the list of
Kotlin/Native targets. The `target` column specifies the name used in the
`KonanTarget` instances. The `tier` column is the level of support provided by
JetBrains for the Kotlin/Native target.

[2] The deprecated targets are scheduled to be removed in Kotlin 1.9.20.

[3] The tests for these targets are not supported by Kotlin/Native. A gradle
`nativeTest` task is not available for this configuration.

[4] The tests fail with Kotlin 1.7.20. There is a fix for this in the Kotlin
1.8.0 release. See [KT-54814](https://youtrack.jetbrains.com/issue/KT-54814).

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

### konan.target
The `konan.target` build property configures the Kotlin/Native build target.
This is used by the GitHub Actions in the native build's matrix configuration.

### maven.sign
The `maven.sign` build property configures whether the `publish` actions should
sign the artifacts using the configured signing key. This is required when
publishing artifacts to Maven Central.

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
