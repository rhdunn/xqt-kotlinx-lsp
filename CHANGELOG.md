# Change Log
> Release notes for `xqt-kotlinx-lsp`.

This format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Fixed

- Improve the build infrastructure to support automated deployment.
- Include all the supported JVM target variants in the `kotlinMultiplatform` module metadata.

### Changed

- The names of the native artifacts now use the target name to support multiple
  native target maven artifact releases.
- The names of the JVM artifacts now use the JVM target version to support
  multiple target JVM maven artifact releases.
- Switched to the [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) format
  for changelogs.

## [1.0.0] - 2023-04-16

### Added

- Implementation of the
  [VSCode Client/Server Language Protocol 1.x](https://github.com/microsoft/language-server-protocol/blob/main/versions/protocol-1-x.md)
  specification.
- JSON serialization support for the LSP types.
- DSL methods for LSP notifications and requests.
- APIs for sending LSP notifications and requests.
- LSP-based JSON-RPC server.
- `stdio` communication channel.

[Unreleased]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/1.0.0...HEAD
[1.0.0]: https://github.com/rhdunn/xqt-kotlinx-lsp/releases/tag/1.0.0
