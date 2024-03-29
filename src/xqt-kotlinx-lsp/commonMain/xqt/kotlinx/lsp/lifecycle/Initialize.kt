// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.Integer
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.lsp.types.TraceValue
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonProperty
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * The `initialize` request parameters.
 *
 * @since 1.0.0
 */
data class InitializeParams(
    /**
     * The process ID of the parent process that started the server.
     *
     * In LSP 2.0.0 this is null if the process has not been started by another process.
     *
     * If the parent process is not alive then the server should exit (see exit notification) its process.
     */
    val processId: Int? = null,

    /**
     * The rootPath of the workspace.
     *
     * This is null if no folder is open.
     */
    @Deprecated(
        "LSP 3.0.0: Deprecated in favour of rootUri.",
        replaceWith = ReplaceWith("rootUri")
    )
    val rootPath: JsonProperty<String> = JsonProperty.missing(),

    /**
     * The rootUri of the workspace.
     *
     * This is null if no folder is open.
     *
     * If both `rootPath` and `rootUri` are set `rootUri` wins.
     */
    val rootUri: JsonProperty<DocumentUri> = JsonProperty.missing(),

    /**
     * User provided initialization options.
     *
     * @since 2.0.0 (LSP 2.1.0)
     */
    val initializationOptions: JsonElement? = null,

    /**
     * The capabilities provided by the client (editor or tool).
     */
    val capabilities: ClientCapabilities,

    /**
     * The initial trace setting.
     *
     * If omitted trace is disabled (`off`).
     */
    val trace: TraceValue? = null
) {
    @Suppress("DEPRECATION")
    companion object : JsonSerialization<InitializeParams> {
        override fun serializeToJson(value: InitializeParams): JsonObject = buildJsonObject {
            putNullable("processId", value.processId, Integer)
            putProperty("rootPath", value.rootPath, JsonString)
            putProperty("rootUri", value.rootUri, DocumentUri)
            putOptional("initializationOptions", value.initializationOptions, LSPAny)
            put("capabilities", value.capabilities, ClientCapabilities)
            putOptional("trace", value.trace, TraceValue)
        }

        override fun deserialize(json: JsonElement): InitializeParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeParams(
                processId = json.getNullable("processId", Integer),
                rootPath = json.getProperty("rootPath", JsonString),
                rootUri = json.getProperty("rootUri", DocumentUri),
                initializationOptions = json.getOptional("initializationOptions", LSPAny),
                capabilities = json.get("capabilities", ClientCapabilities),
                trace = json.getOptional("trace", TraceValue)
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
 * Value-object describing what options formatting should use.
 *
 * @since 1.0.0 (LSP 2.1.0)
 */
data class ClientCapabilities(
    /**
     * Signature for further options.
     *
     * In this implementation all options are included in the map.
     */
    private val options: Map<String, JsonElement> = mapOf()
) : Map<String, JsonElement> by options {
    constructor(vararg options: Pair<String, JsonElement>) : this(options = jsonObjectOf(*options))

    companion object : JsonSerialization<ClientCapabilities> {
        override fun serializeToJson(value: ClientCapabilities): JsonObject = JsonObject(value.options)

        override fun deserialize(json: JsonElement): ClientCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ClientCapabilities(
                options = json
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
 * The error object of an initialize request.
 *
 * @param code a number indicating the error type that occurred
 * @param message a string providing a short description of the error
 * @param data the optional initialization error
 *
 * @since 1.0.0
 */
data class InitializeErrorObject(
    override val code: ErrorCode,
    override val message: String,
    override val data: InitializeError? = null
) : TypedErrorObject<InitializeError> {
    companion object : TypedErrorObjectConverter<InitializeError> {
        override fun convert(error: ErrorObject): TypedErrorObject<InitializeError> = InitializeErrorObject(
            code = error.code,
            message = error.message,
            data = error.data?.let { InitializeError.deserialize(it) }
        )
    }
}

/**
 * The response of an initialize request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class InitializeResponse(
    override val id: JsonIntOrString?,
    override val result: InitializeResult?,
    override val error: TypedErrorObject<InitializeError>?,
    override val jsonrpc: String
) : TypedResponseObject<InitializeResult?, InitializeError> {
    companion object : TypedResponseObjectConverter<InitializeResult?, InitializeError> {
        override fun convert(response: ResponseObject): TypedResponseObject<InitializeResult?, InitializeError> {
            return InitializeResponse(
                id = response.id,
                result = response.result?.let { InitializeResult.deserialize(it) },
                error = response.error?.let { InitializeErrorObject.convert(it) },
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The initialize request is sent as the first request from the client to the server.
 *
 * If the server receives request or notification before the `initialize` request it
 * should act as follows:
 * * for a request the response should be errored with `code: -32002` (`ServerNotInitialized`).
 *   The message can be picked by the server.
 * * notifications should be dropped.
 *
 * @return an initialize result response
 *
 * @since 1.0.0
 */
fun RequestMessage.initialize(
    handler: InitializeParams.() -> InitializeResult
): Unit = method(
    method = LifecycleRequest.INITIALIZE,
    handler = handler,
    paramsSerializer = InitializeParams,
    resultSerializer = InitializeResult
)

/**
 * Send an initialize request to the server.
 *
 * If the server receives request or notification before the `initialize` request it
 * should act as follows:
 * * for a request the response should be errored with `code: -32001`.
 *   The message can be picked by the server.
 * * notifications should be dropped.
 *
 * Until the server has responded to the `initialize` request with an `InitializeResult`
 * the client must not send any additional requests or notifications to the server.
 *
 * In LSP 3.0.0 or later, during the `initialize` request the server is allowed to send
 * the notifications `window/showMessage`, `window/logMessage` and `telemetry/event` as
 * well as the `window/showMessageRequest` request to the client.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun JsonRpcServer.initialize(
    params: InitializeParams,
    responseHandler: (TypedResponseObject<InitializeResult?, InitializeError>.() -> Unit)? = null
): JsonIntOrString = sendRequest(
    method = LifecycleRequest.INITIALIZE,
    params = InitializeParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = InitializeResponse
)

/**
 * Send an initialize request to the server.
 *
 * The `rootPath` or `rootUri` is null if no folder is open. If both `rootPath` and `rootUri`
 * are set `rootUri` wins.
 *
 * If the server receives request or notification before the `initialize` request it
 * should act as follows:
 * * for a request the response should be errored with `code: -32001` (`ServerNotInitialized`).
 *   The message can be picked by the server.
 * * notifications should be dropped.
 *
 * Until the server has responded to the `initialize` request with an `InitializeResult`
 * the client must not send any additional requests or notifications to the server.
 *
 * In LSP 2.0.0 or later, `processId` is null if the process has not been started by another
 * process.
 *
 * In LSP 3.0.0 or later, during the `initialize` request the server is allowed to send
 * the notifications `window/showMessage`, `window/logMessage` and `telemetry/event` as
 * well as the `window/showMessageRequest` request to the client.
 *
 * @param processId the process ID of the parent process that started the server
 * @param rootPath the rootPath of the workspace
 * @param rootUri the rootUri of the workspace
 * @param capabilities the capabilities provided by the client (editor)
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun JsonRpcServer.initialize(
    processId: Int? = null,
    rootPath: JsonProperty<String> = JsonProperty.missing(),
    rootUri: JsonProperty<DocumentUri> = JsonProperty.missing(),
    capabilities: ClientCapabilities,
    responseHandler: (TypedResponseObject<InitializeResult?, InitializeError>.() -> Unit)? = null
): JsonIntOrString = initialize(
    params = InitializeParams(
        processId = processId,
        rootPath = rootPath,
        rootUri = rootUri,
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
