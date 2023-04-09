// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextDocumentPositionParams
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedObjectOrArray

/**
 * The `textDocument/hover` request response.
 *
 * @since 1.0.0
 */
data class Hover(
    /**
     * The hover's content.
     */
    val contents: List<MarkedString>,

    /**
     * An optional range is a range inside a text document that is used to
     * visualize a hover, e.g. by changing the background color.
     */
    val range: Range? = null
) {
    companion object : JsonSerialization<Hover> {
        private val MarkedStringArray = JsonTypedObjectOrArray(MarkedString)

        override fun serializeToJson(value: Hover): JsonObject = buildJsonObject {
            put("contents", value.contents, MarkedStringArray)
            putOptional("range", value.range, Range)
        }

        override fun deserialize(json: JsonElement): Hover = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Hover(
                contents = json.get("contents", MarkedStringArray),
                range = json.getOptional("range", Range)
            )
        }
    }
}

/**
 * Render human-readable text or code-block.
 *
 * @since 1.0.0
 */
data class MarkedString(
    /**
     * The language of the markdown code-block.
     */
    val language: String? = null,

    /**
     * The contents of the markdown or code-block.
     */
    val value: String
) {
    companion object : JsonSerialization<MarkedString> {
        override fun serializeToJson(value: MarkedString): JsonElement = when (value.language) {
            null -> JsonPrimitive(value.value)
            else -> buildJsonObject {
                put("language", value.language, JsonString)
                put("value", value.value, JsonString)
            }
        }

        override fun deserialize(json: JsonElement): MarkedString = when (json) {
            is JsonPrimitive -> when (json.kindType) {
                KindType.String -> MarkedString(value = json.content)
                else -> unsupportedKindType(json)
            }

            is JsonObject -> MarkedString(
                language = json.get("language", JsonString),
                value = json.get("value", JsonString)
            )

            else -> unsupportedKindType(json)
        }
    }
}

/**
 * The response of a hover request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class HoverResponse(
    override val id: JsonIntOrString?,
    override val result: Hover?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<Hover?, JsonElement> {
    companion object : TypedResponseObjectConverter<Hover?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<Hover?, JsonElement> {
            return HoverResponse(
                id = response.id,
                result = response.result?.let { Hover.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The hover request is sent from the client to the server to request hover information at
 * a given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @since 2.0.0
 */
fun TextDocumentRequest.hover(
    handler: TextDocumentPositionParams.() -> Hover
): Unit = request.method(
    method = TextDocumentRequest.HOVER,
    handler = handler,
    paramsSerializer = TextDocumentPositionParams,
    resultSerializer = Hover
)

/**
 * The hover request is sent from the client to the server to request hover information at
 * a given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.hover(
    params: TextDocumentPositionParams,
    responseHandler: (TypedResponseObject<Hover?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.HOVER,
    params = TextDocumentPositionParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = HoverResponse
)

/**
 * The hover request is sent from the client to the server to request hover information at
 * a given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param textDocument the text document
 * @param position the position inside the text document
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.hover(
    textDocument: TextDocumentIdentifier,
    position: Position,
    responseHandler: (TypedResponseObject<Hover?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = hover(
    params = TextDocumentPositionParams(
        textDocument = textDocument,
        position = position
    ),
    responseHandler = responseHandler
)
