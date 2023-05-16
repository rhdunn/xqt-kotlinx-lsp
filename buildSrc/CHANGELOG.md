# Change Log
> Release notes for the Kotlin multiplatform project template.

This format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

- Don't run the workflows when pushing tags to the repository.

## [1.2.0] - 2023-05-16

### Added

- A `SupportedVariants` enumeration type.
- `jvm.variants` and `konan.variants` build configuration properties.
- `jvmMain` and `jvmTest` DSL helper methods taking a `Named` target.
- The readme file now includes:
    - table of contents
    - documentation links
    - information for adding the library as a Maven dependency
    - maven central and license badges

### Fixed

- Use `jvm` for the Kotlin/JVM documentation.
- Use `native` for the Kotlin/Native documentation.

## [1.1.0] - 2023-05-14

### Added

- A `jvmName` DSL helper method.
- `jvmMain`, `jvmTest`, and `jvmArtifactId` DSL helper methods taking a `JavaVersion`.
- A `KonanTarget.publicationName` DSL helper property.
- `nativeMain` and `nativeTest` DSL helper methods taking a `Named` target.

### Fixed

- Resolve dependency variants locally for e.g. `kotlin-test`.
- Include all the supported JVM target variants in the `kotlinMultiplatform` module metadata.
- Include all the supported native target variants in the `kotlinMultiplatform` module metadata.

## [1.0.0] - 2023-05-10

### Added

- `.gitignore` rules for IntelliJ IDE and gradle.
- Kotlin Multiplatform configuration for JS, Native, and JVM.
- Publication artifact signing.
- Configuring and deploying to Maven Local and Maven Central.
- Build and publish the Dokka documentation to GitHub Pages.
- Build and optionally deploy the various targets to Maven Central.

[Unreleased]: https://github.com/rhdunn/kotlin-multilpatform-template/compare/1.0.0...HEAD
[1.2.0]: https://github.com/rhdunn/kotlin-multilpatform-template/compare/1.1.0...1.2.0
[1.1.0]: https://github.com/rhdunn/kotlin-multilpatform-template/compare/1.0.0...1.1.0
[1.0.0]: https://github.com/rhdunn/kotlin-multilpatform-template/releases/tag/1.0.0
