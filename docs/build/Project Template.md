# Project Template
The `kotlin-multiplatform-template` project is an open-source template that
provides common infrastructure for creating and deploying Kotlin multiplatform
projects.

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

## Documentation
This template provides a [build-docs.yml](../../.github/workflows/build-docs.yml)
GitHub Action that builds the Dokka HTML documentation and deploys that to the
project's GitHub pages.

To enable this:
1. Go to the "Settings" page for the project.
2. Click on the "Pages" section of the settings in the side pane.
3. Under "Build and deployment", set the "Source" to "GitHub Actions".

## Project Template
This template provides a template module in the
[buildSrc/template](../../buildSrc/template) directory that can be copied
into your subprojects to use as the starting point for the subproject module.

This provides support for:
1. Configuring the necessary plugins.
2. Setting the group ID and version for the package from the project metadata.
3. Configuration for Kotlin JS, JVM, and Native.
4. Configuration for Dokka documentation.
5. Configuration for Maven POM files, singing, and publishing.

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

---
Copyright (C) 2023 Reece H. Dunn
