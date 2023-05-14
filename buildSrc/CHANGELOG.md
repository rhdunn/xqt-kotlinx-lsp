# Change Log
> Release notes for the Kotlin multiplatform project template.

This format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- A `jvmName` DSL helper method.
- `jvmMain`, `jvmTest`, and `jvmArtifactId` DSL helper methods taking a `JavaVersion`.
- A `KonanTarget.publicationName` DSL helper property.
- `nativeMain` and `nativeTest` DSL helper methods taking a `Named` target.

### Fixed

- Resolving dependency variants locally for e.g. `kotlin-test`.

## [1.0.0] - 2023-05-10

### Added

- `.gitignore` rules for IntelliJ IDE and gradle.
- Kotlin Multiplatform configuration for JS, Native, and JVM.
- Publication artifact signing.
- Configuring and deploying to Maven Local and Maven Central.
- Build and publish the Dokka documentation to GitHub Pages.
- Build and optionally deploy the various targets to Maven Central.

[Unreleased]: https://github.com/rhdunn/kotlin-multilpatform-template/compare/1.0.0...HEAD
[1.0.0]: https://github.com/rhdunn/kotlin-multilpatform-template/releases/tag/1.0.0
