// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.*
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
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
        internal const val INITIALIZE: String = "initialize"

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
data class InitializeResult(
    /**
     * The capabilities provided by the language server.
     */
    val capabilities: ServerCapabilities
) {
    companion object : JsonSerialization<InitializeResult> {
        override fun serializeToJson(value: InitializeResult): JsonObject = buildJsonObject {
            put("capabilities", value.capabilities, ServerCapabilities)
        }

        override fun deserialize(json: JsonElement): InitializeResult = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeResult(
                capabilities = json.get("capabilities", ServerCapabilities)
            )
        }
    }
}

/**
 * The `initialize` request error data.
 *
 * @since 1.0.0
 */
data class InitializeError(
    /**
     * Indicates whether the client should retry to send the
     * initialize request after showing the message provided
     * in the ResponseError.
     */
    val retry: Boolean
) {
    companion object : JsonSerialization<InitializeError> {
        override fun serializeToJson(value: InitializeError): JsonObject = buildJsonObject {
            put("retry", value.retry, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): InitializeError = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeError(
                retry = json.get("retry", JsonBoolean)
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

/**
 * The response of an initialize request.
 *
 * @param response The associated response message.
 *
 * @since 1.0.0
 */
data class InitializeResponse(private val response: ResponseMessage) {
    /**
     * The result of a successful initialize request.
     */
    val result: InitializeResult? by lazy {
        response.result?.let { InitializeResult.deserialize(it) }
    }

    /**
     * The result of a failed initialize request.
     */
    val initializeError: InitializeError? by lazy {
        response.error?.data?.let { InitializeError.deserialize(it) }
    }

    /**
     * The error object in case a request fails.
     */
    val error: ErrorObject?
        get() = response.error
}

/**
 * The initialize request is sent as the first request from the client to the server.
 *
 * @return an initialize result response
 *
 * @since 1.0.0
 */
fun RequestMessage.initialize(handler: InitializeParams.() -> InitializeResult) {
    if (method == InitializeParams.INITIALIZE) {
        val result = params(InitializeParams).handler()
        sendResult(result, InitializeResult)
    }
}

/**
 * Send an initialize request to the server.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun JsonRpcServer.initialize(
    params: InitializeParams,
    responseHandler: (InitializeResponse.() -> Unit)? = null
): JsonIntOrString = sendRequest(
    method = InitializeParams.INITIALIZE,
    params = InitializeParams.serializeToJson(params),
    responseHandler = responseHandler?.let {
        { response: ResponseMessage -> responseHandler(InitializeResponse(response)) }
    }
)

/**
 * Send an initialize request to the server.
 *
 * The `rootPath` is null if no folder is open.
 *
 * @param processId the process ID of the parent process that started the server
 * @param rootPath the rootPath of the workspace
 * @param capabilities the capabilities provided by the client (editor)
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun JsonRpcServer.initialize(
    processId: Int,
    rootPath: String? = null,
    capabilities: JsonObject,
    responseHandler: (InitializeResponse.() -> Unit)? = null
): JsonIntOrString = initialize(
    params = InitializeParams(
        processId = processId,
        rootPath = rootPath,
        capabilities = capabilities
    ),
    responseHandler = responseHandler
)

/**
 * The `initialize` request error data.
 *
 * @param message a string providing a short description of the error
 * @param code a number indicating the error type that occurred
 * @param data contains additional information about the error
 *
 * @since 1.0.0
 */
@Suppress("FunctionName", "UnusedReceiverParameter")
fun InitializeParams.InitializeError(
    message: String,
    data: InitializeError,
    code: ErrorCode = ErrorCodes.InternalError
): ErrorObject = ErrorObject(
    code = code,
    message = message,
    data = InitializeError.serializeToJson(data)
)

/**
 * The `initialize` request error data.
 *
 * @param message a string providing a short description of the error
 * @param code a number indicating the error type that occurred
 * @param retry indicates whether the client should retry to send the initialize
 *        request after showing the message provided in the ResponseError
 *
 * @since 1.0.0
 */
@Suppress("FunctionName")
fun InitializeParams.InitializeError(
    message: String,
    retry: Boolean = false,
    code: ErrorCode = ErrorCodes.InternalError
): ErrorObject = InitializeError(
    code = code,
    message = message,
    data = InitializeError(retry = retry)
)
