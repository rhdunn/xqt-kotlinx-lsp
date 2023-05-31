# Change Log
> Release notes for `xqt-kotlinx-lsp`.

This format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

Implementation of the
[Language Server Protocol 3.0.0](https://github.com/microsoft/language-server-protocol/blob/3.0.0/protocol.md)
specification.

### Added

Base Protocol:
- `ServerNotInitialized` and `UnknownErrorCode` error codes.

Types:
- `DocumentUri`
- `DocumentFilter`
- `DocumentSelector`

### Changed

- Updated the API documentation.

Types:
- `Location` -- Changed the `uri` parameter to a `DocumentUri`.
- `WorkspaceEdit` -- Changed the `changes` parameter key to a `DocumentUri`.
- `TextDocumentIdentifier` -- Changed the `uri` parameter to a `DocumentUri`.
- `TextDocumentItem` -- Changed the `uri` parameter to a `DocumentUri`.

### Dependency Upgrades

- Upgrade to XQT Kotlinx JSON RPC 1.1.0 snapshot.

## [2.0.1] - 2023-05-22

### Added

- Add support for the Android Kotlin/Native targets.
- Add support for the `watchos_device_arm64` Kotlin/Native target.

### Removed

- Remove support for the deprecated Kotlin/JS legacy target.
- Remove support for the deprecated Kotlin/Native targets.

### Changed

- Default to building with gradle 8.1.1.

### Dependency Upgrades

- Upgrade to Gradle 8.0 and later.
- Upgrade to Kotlin 1.8.21.
- Upgrade to Dokka 1.8.10.
- Upgrade to JUnit 5.9.3.
- Upgrade to Kotlinx Serialization 1.5.1.
- Upgrade to XQT Kotlinx JSON RPC 1.0.3.

## [2.0.0] - 2023-05-19

Implementation of the
[Language Server Protocol 2.x](https://github.com/microsoft/language-server-protocol/blob/main/versions/protocol-2-x.md)
specification.

### Added

Notifications and Requests:
- `$/cancelRequest`
- `window/showMessageRequest`
- `telemetry/event`
- `textDocument/didSave`
- `textDocument/documentLink`
- `documentLink/resolve`

Types:
- `TextEditArray`
- `TextDocumentItem`
- `VersionedTextDocumentIdentifier`

Parameters:
- `InitializeParams` -- Add an `initializeOptions` parameter.

### Changed

- Updated the API documentation.

Notifications and Requests:
- `initialize` -- Make the `processId` parameter nullable.
- `initialize` -- Make `capabilities` an instance of `ClientCapabilities`.
- `textDocument/completion` -- Return a `CompletionList` instead of a `List<CompletionItem>`.
  > __NOTE:__ The following notifications and requests are incompatible with LSP 1.x clients/servers.
- `textDocument/codeLens` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/completion` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/definition` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/didChange` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/didClose` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/didOpen` -- Replace the `uri` and `text` parameters with `textDocument`.
- `textDocument/documentHighlight` -- Replace the `uri` parameter with `textDocument`; return
  a `List<DocumentHighlight>` instead of a `DocumentHighlight` object.
- `textDocument/documentSymbol` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/hover` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/references` -- Replace the `uri` parameter with `textDocument`.
- `textDocument/signatureHelp` -- Replace the `uri` parameter with `textDocument`.

Types:
- Renamed `TextDocumentPosition` to `TextDocumentPositionParams`.
- Don't implement `TextDocumentIdentifier` on `FileEvent`.
- `CompletionItem` -- Add `additionalTextEdits` and `command` properties.

## [1.0.1] - 2023-05-16

### Fixed

- Improve the build infrastructure to support automated deployment.
- Include all the supported JVM target variants in the `kotlinMultiplatform` module metadata.
- Include all the supported native target variants in the `kotlinMultiplatform` module metadata.

### Changed

- The names of the native artifacts now use the target name to support multiple
  native target maven artifact releases.
- The names of the JVM artifacts now use the JVM target version to support
  multiple target JVM maven artifact releases.
- Switched to the [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) format
  for changelogs.

### Dependency Upgrades

- Upgrade to XQT Kotlinx JSON RPC 1.0.2.

## [1.0.0] - 2023-04-16

Implementation of the
[VSCode Client/Server Language Protocol 1.x](https://github.com/microsoft/language-server-protocol/blob/main/versions/protocol-1-x.md)
specification.

### Added

Base Protocol:
- Base Types from LSP 3.16 -- `Integer`, `UInteger`, and `Decimal`
- Base Types from LSP 3.17 -- `LSPAny`, `LSPObject`, and `LSPArray`
- Abstract Message -- `Message`
- Request Message -- `RequestMessage`
- Response Message -- `ResponseMessage`, `ResponseError`, `ErrorCodes`, and error constructor functions.
- Notification Message -- `NotificationMessage`

Transport:
- `LspJsonRpcServer`
- `stdio`

Notifications and Requests:
- `initialize`
- `shutdown`
- `exit`
- `window/showMessage`
- `window/logMessage`
- `workspace/didChangeConfiguration`
- `textDocument/didOpen`
- `textDocument/didChange`
- `textDocument/didClose`
- `workspace/didChangeWatchedFiles`
- `textDocument/publishDiagnostics`
- `textDocument/completion`
- `completionItem/resolve`
- `textDocument/hover`
- `textDocument/signatureHelp`
- `textDocument/definition`
- `textDocument/references`
- `textDocument/documentHighlight`
- `textDocument/documentSymbol`
- `workspace/symbol`
- `textDocument/codeAction`
- `textDocument/codeLens`
- `codeLens/resolve`
- `textDocument/formatting`
- `textDocument/rangeFormatting`
- `textDocument/onTypeFormatting`
- `textDocument/rename`

Types:
- `Position`
- `Range`
- `Location`
- `Diagnostic` and `DiagnosticSeverity`
- `Command`
- `TextEdit`
- `WorkspaceEdit`
- `TextDocumentIdentifier`
- `TextDocumentPosition`

[Unreleased]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/2.0.1...HEAD
[2.0.1]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/2.0.0...2.0.1
[2.0.0]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/1.0.1...2.0.0
[1.0.1]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/1.0.0...1.0.1
[1.0.0]: https://github.com/rhdunn/xqt-kotlinx-lsp/releases/tag/1.0.0
