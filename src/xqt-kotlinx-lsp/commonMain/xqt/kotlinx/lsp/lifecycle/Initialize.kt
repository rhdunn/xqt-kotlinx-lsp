// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.Integer
import xqt.kotlinx.lsp.base.LSPObject
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * The `initialize` request parameters.
 *
 * @since 1.0.0
 */
data class InitializeParams(
    /**
     * The process ID of the parent process that started the server.
     */
    val processId: Int,

    /**
     * The rootPath of the workspace.
     *
     * This is null if no folder is open.
     */
    val rootPath: String? = null,

    /**
     * The capabilities provided by the client (editor).
     */
    val capabilities: JsonObject
) {
    companion object : JsonSerialization<InitializeParams> {
        override fun serializeToJson(value: InitializeParams): JsonObject = buildJsonObject {
            put("processId", value.processId, Integer)
            putNullable("rootPath", value.rootPath, JsonString)
            put("capabilities", value.capabilities, LSPObject)
        }

        override fun deserialize(json: JsonElement): InitializeParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeParams(
                processId = json.get("processId", Integer),
                rootPath = json.getNullable("rootPath", JsonString),
                capabilities = json.get("capabilities", LSPObject)
            )
        }
    }
}

/**
 * The `initialize` request response.
 *
 * @since 1.0.0
 */
data class InitializeResults(
    /**
     * The capabilities provided by the language server.
     */
    val capabilities: ServerCapabilities
) {
    companion object : JsonSerialization<InitializeResults> {
        override fun serializeToJson(value: InitializeResults): JsonObject = buildJsonObject {
            put("capabilities", value.capabilities, ServerCapabilities)
        }

        override fun deserialize(json: JsonElement): InitializeResults = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeResults(
                capabilities = json.get("capabilities", ServerCapabilities)
            )
        }
    }
}

/**
 * Server capabilities.
 *
 * @since 1.0.0
 */
data class ServerCapabilities(
    /**
     * Defines how text documents are synced.
     */
    val textDocumentSync: TextDocumentSyncKind? = null,

    /**
     * The server provides hover support.
     */
    val hoverProvider: Boolean? = null,

    /**
     * The server provides completion support.
     */
    val completionProvider: CompletionOptions? = null,

    /**
     * The server provides signature help support.
     */
    val signatureHelpProvider: SignatureHelpOptions? = null,

    /**
     * The server provides goto definition support.
     */
    val definitionProvider: Boolean? = null,

    /**
     * The server provides find references support.
     */
    val referencesProvider: Boolean? = null,

    /**
     * The server provides document highlight support.
     */
    val documentHighlightProvider: Boolean? = null,

    /**
     * The server provides document symbol support.
     */
    val documentSymbolProvider: Boolean? = null,

    /**
     * The server provides workspace symbol support.
     */
    val workspaceSymbolProvider: Boolean? = null,

    /**
     * The server provides code actions.
     */
    val codeActionProvider: Boolean? = null,

    /**
     * The server provides code lens.
     */
    val codeLensProvider: CodeLensOptions? = null,

    /**
     * The server provides document formatting.
     */
    val documentFormattingProvider: Boolean? = null,

    /**
     * The server provides document range formatting.
     */
    val documentRangeFormattingProvider: Boolean? = null,

    /**
     * The server provides document formatting on typing.
     */
    val documentOnTypeFormattingProvider: DocumentOnTypeFormattingOptions? = null,

    /**
     * The server provides rename support.
     */
    val renameProvider: Boolean? = null
) {
    companion object : JsonSerialization<ServerCapabilities> {
        override fun serializeToJson(value: ServerCapabilities): JsonObject = buildJsonObject {
            putOptional("textDocumentSync", value.textDocumentSync, TextDocumentSyncKind)
            putOptional("hoverProvider", value.hoverProvider, JsonBoolean)
            putOptional("completionProvider", value.completionProvider, CompletionOptions)
            putOptional("signatureHelpProvider", value.signatureHelpProvider, SignatureHelpOptions)
            putOptional("definitionProvider", value.definitionProvider, JsonBoolean)
            putOptional("referencesProvider", value.referencesProvider, JsonBoolean)
            putOptional("documentHighlightProvider", value.documentHighlightProvider, JsonBoolean)
            putOptional("documentSymbolProvider", value.documentSymbolProvider, JsonBoolean)
            putOptional("workspaceSymbolProvider", value.workspaceSymbolProvider, JsonBoolean)
            putOptional("codeActionProvider", value.codeActionProvider, JsonBoolean)
            putOptional("codeLensProvider", value.codeLensProvider, CodeLensOptions)
            putOptional("documentFormattingProvider", value.documentFormattingProvider, JsonBoolean)
            putOptional("documentRangeFormattingProvider", value.documentRangeFormattingProvider, JsonBoolean)
            putOptional(
                "documentOnTypeFormattingProvider",
                value.documentOnTypeFormattingProvider,
                DocumentOnTypeFormattingOptions
            )
            putOptional("renameProvider", value.renameProvider, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): ServerCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ServerCapabilities(
                textDocumentSync = json.getOptional("textDocumentSync", TextDocumentSyncKind),
                hoverProvider = json.getOptional("hoverProvider", JsonBoolean),
                completionProvider = json.getOptional("completionProvider", CompletionOptions),
                signatureHelpProvider = json.getOptional("signatureHelpProvider", SignatureHelpOptions),
                definitionProvider = json.getOptional("definitionProvider", JsonBoolean),
                referencesProvider = json.getOptional("referencesProvider", JsonBoolean),
                documentHighlightProvider = json.getOptional("documentHighlightProvider", JsonBoolean),
                documentSymbolProvider = json.getOptional("documentSymbolProvider", JsonBoolean),
                workspaceSymbolProvider = json.getOptional("workspaceSymbolProvider", JsonBoolean),
                codeActionProvider = json.getOptional("codeActionProvider", JsonBoolean),
                codeLensProvider = json.getOptional("codeLensProvider", CodeLensOptions),
                documentFormattingProvider = json.getOptional("documentFormattingProvider", JsonBoolean),
                documentRangeFormattingProvider = json.getOptional("documentRangeFormattingProvider", JsonBoolean),
                documentOnTypeFormattingProvider = json.getOptional(
                    "documentOnTypeFormattingProvider",
                    DocumentOnTypeFormattingOptions
                ),
                renameProvider = json.getOptional("renameProvider", JsonBoolean),
            )
        }
    }
}
