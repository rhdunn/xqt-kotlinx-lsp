# Change Log
> Release notes for `xqt-kotlinx-lsp`.

This format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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

## [1.0.0] - 2023-04-16

- Implementation of the
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

[Unreleased]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/1.0.1...HEAD
[1.0.1]: https://github.com/rhdunn/xqt-kotlinx-lsp/compare/1.0.0...1.0.1
[1.0.0]: https://github.com/rhdunn/xqt-kotlinx-lsp/releases/tag/1.0.0
