# xqt-kotlinx-lsp
> Kotlin multiplatform Language Server Protocol (LSP) library

The `xqt-kotlinx-lsp` library is an open-source implementation of the LSP
(Language Server Protocol) specification. It supports:
1. Language Server Protocol (LSP)
   [1.x](https://github.com/microsoft/language-server-protocol/blob/main/versions/protocol-1-x.md)

## Maven Central
The `xqt-kotlinx-lsp` binaries are available on Maven Central:

1. Gradle (Groovy DSL)
   ```
   implementation 'io.github.rhdunn:xqt-kotlinx-lsp:1.0.0'
   ```

2. Gradle (Kotlin DSL)
   ```
   implementation("io.github.rhdunn:xqt-kotlinx-lsp:1.0.0")
   ```

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
Copyright (C) 2022-2023 Reece H. Dunn

SPDX-License-Identifier: [Apache-2.0](LICENSE)
