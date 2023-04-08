// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.lsp.types.TextEditArray
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonPrimitiveValue
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * The `textDocument/formatting` request parameters.
 *
 * @since 1.0.0
 */
data class DocumentFormattingParams(
    /**
     * The document to format.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The format options.
     */
    val options: FormattingOptions
) {
    companion object : JsonSerialization<DocumentFormattingParams> {
        override fun serializeToJson(value: DocumentFormattingParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("options", value.options, FormattingOptions)
        }

        override fun deserialize(json: JsonElement): DocumentFormattingParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentFormattingParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                options = json.get("options", FormattingOptions)
            )
        }
    }
}

/**
 * Value-object describing what options formatting should use.
 *
 * @since 1.0.0
 */
data class FormattingOptions(
    /**
     * Size of a tab in spaces.
     */
    val tabSize: UInt,

    /**
     * Prefer using spaces over tabs.
     */
    val insertSpaces: Boolean,

    /**
     * Signature for further options.
     *
     * In this implementation all options are included in the map.
     */
    private val options: Map<String, JsonPrimitive>
) : Map<String, JsonPrimitive> by options {
    constructor(
        tabSize: UInt,
        insertSpaces: Boolean
    ) : this(
        tabSize = tabSize,
        insertSpaces = insertSpaces,
        options = mapOf(
            "tabSize" to UInteger.serializeToJson(tabSize),
            "insertSpaces" to JsonBoolean.serializeToJson(insertSpaces)
        )
    )

    companion object : JsonSerialization<FormattingOptions> {
        override fun serializeToJson(value: FormattingOptions): JsonObject = JsonObject(value.options)

        override fun deserialize(json: JsonElement): FormattingOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> FormattingOptions(
                tabSize = json.get("tabSize", UInteger),
                insertSpaces = json.get("insertSpaces", JsonBoolean),
                options = json.mapValues { (_, value) -> JsonPrimitiveValue.deserialize(value) }
            )
        }
    }
}

/**
 * The response of a code action request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class FormattingResponse(
    override val id: JsonIntOrString?,
    override val result: List<TextEdit>,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<List<TextEdit>, JsonElement> {
    companion object : TypedResponseObjectConverter<List<TextEdit>, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<List<TextEdit>, JsonElement> {
            return FormattingResponse(
                id = response.id,
                result = response.result?.let { TextEditArray.deserialize(it) } ?: listOf(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The document formatting request is sent from the server to the client to format a whole
 * document.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.formatting(
    handler: DocumentFormattingParams.() -> List<TextEdit>
): Unit = request.method(
    method = TextDocumentRequest.FORMATTING,
    handler = handler,
    paramsSerializer = DocumentFormattingParams,
    resultSerializer = TextEditArray
)

/**
 * The document formatting request is sent from the server to the client to format a whole
 * document.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.formatting(
    params: DocumentFormattingParams,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.FORMATTING,
    params = DocumentFormattingParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = FormattingResponse
)

/**
 * The document formatting request is sent from the server to the client to format a whole
 * document.
 *
 * @param textDocument the document in which the command was invoked
 * @param options the format options
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.formatting(
    textDocument: TextDocumentIdentifier,
    options: FormattingOptions,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = formatting(
    params = DocumentFormattingParams(
        textDocument = textDocument,
        options = options
    ),
    responseHandler = responseHandler
)
