# Build Properties
The following configuration properties are available to configure the build:

> __NOTE:__ These build properties take precedence over any equivalent
> environment variables.

## Build Variants

### jvm.target
The `jvm.target` build property configures the version of the Java VM to target
by the Kotlin compiler. This is used by the GitHub Actions to build against the
LTS releases of JVM, 11 and 17.

### jvm.variants
The `jvm.variants` build property configures the list of Java VM variants
supported in the main multiplatform publication.

This can be one of the following values:
1. `all` -- Include all JVM targets configured in the project metadata as
   JVM variants. The publications are named `jvm[MAJOR_JVM_VERSION]`.
2. `target-only` -- Only include the `jvm.target` JVM variant. The publication
   is named `jvm`.
3. `none` -- Don't include any JVM variants.

### konan.target
The `konan.target` build property configures the Kotlin/Native build target.
This is used by the GitHub Actions in the native build's matrix configuration.

### konan.variants
The `konan.variants` build property configures the list of Kotlin/Native
variants supported in the main multiplatform publication.

This can be one of the following values:
1. `all` -- Include all native targets configured in the project metadata as
   native variants. The publications are named afer the Konan targets.
2. `target-only` -- Only include the `konan.target` native variant. The
   publication is named `native`.
3. `none` -- Don't include any JVM variants.

## Kotlin/JS Test Browser

### karma.browser
The `karma.browser` build property configures the name of the browser to use in
the Kotlin/JS tests when run on the browser.

### karma.browser.channel
The `karma.browser.channel` build property configures the name of the
development/release channel of the browser used to run the Kotlin/JS tests.

### karma.browser.headless
The `karma.browser.headless` build property determines whether the web browser
runs in headless mode, or with a visible graphical interface.

## Signing Artifacts

### maven.sign
The `maven.sign` build property configures whether the `publish` actions should
sign the artifacts using the configured signing key. This is required when
publishing artifacts to Maven Central.

This can be one of the following values:
1. `env` -- Use the `maven.sign.key.*` build properties or `SIGNING_KEY_*`
   environment variables to sign the artifacts. If the private key is not
   present then the artifacts will not be signed.
2. `gpg`, `true` -- Use the GPG command to sign the artifacts. This will prompt
   the user for the key password.
3. `none`, `false` -- Don't sign the artifacts.

### maven.sign.key.id
The `maven.sign.key.id` build property specifies the key id to use when using
a subkey to sign the artifacts. If a subkey is not used, this should be blank,
or not set.

### maven.sign.key.private
The `maven.sign.key.private` build property specifies the ascii-armoured PGP
private key to sign the artifacts with.

Newlines are represented as `\n`.

### maven.sign.key.password
The `maven.sign.key.password` build property specifies the password or
passphrase associated with the private key.

## Publishing Artifacts

### maven.repository.sonatype
The `maven.repository.sonatype` build property configures publishing artifacts
to Maven Central.

This can be one of the following values:
1. `release` -- Configure the `publishToMavenCentral` targets to use the
   sonatype release URLs.
2. `snapshot` -- Configure the `publishToMavenCentral` targets to use the
   sonatype snapshot URLs.
3. `none` -- Don't publish the artifacts to Maven Central.

## Node JS

### nodejs.download
The `nodejs.download` build property configures whether the build should
download node when building and running the Kotlin/JS targets. If this is false
the build will use the system's node installation. This is used by the GitHub
Actions to prevent node being downloaded during the build.

## Open Source Software Repository Hosting

### ossrh.username
The `ossrh.username` build property specifies the Open Source Software
Repository Hosting (OSSRH) username to use when publishing artifacts to Maven
Central.

### ossrh.password
The `ossrh.password` build property specifies the Open Source Software
Repository Hosting (OSSRH) password to use when publishing artifacts to Maven
Central.

---
Copyright (C) 2023 Reece H. Dunn
