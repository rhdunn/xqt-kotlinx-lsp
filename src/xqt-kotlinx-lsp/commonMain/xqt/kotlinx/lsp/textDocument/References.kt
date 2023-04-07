// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * The `textDocument/references` request parameters.
 *
 * @since 1.0.0
 */
data class ReferencesParams(
    override val uri: String,
    override val position: Position,

    /**
     * The reference context.
     */
    val context: ReferenceContext
) : TextDocumentPosition {
    companion object : JsonSerialization<ReferencesParams> {
        override fun serializeToJson(value: ReferencesParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("position", value.position, Position)
            put("context", value.context, ReferenceContext)
        }

        override fun deserialize(json: JsonElement): ReferencesParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferencesParams(
                uri = json.get("uri", JsonString),
                position = json.get("position", Position),
                context = json.get("context", ReferenceContext)
            )
        }
    }
}

/**
 * The reference context.
 *
 * @since 1.0.0
 */
data class ReferenceContext(
    /**
     * Include the declaration of the current symbol.
     */
    val includeDeclaration: Boolean
) {
    companion object : JsonSerialization<ReferenceContext> {
        override fun serializeToJson(value: ReferenceContext): JsonObject = buildJsonObject {
            put("includeDeclaration", value.includeDeclaration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): ReferenceContext = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferenceContext(
                includeDeclaration = json.get("includeDeclaration", JsonBoolean)
            )
        }
    }
}

private val LocationArray = JsonTypedArray(Location)

/**
 * The response of a definition request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class ReferencesResponse(
    override val id: JsonIntOrString?,
    override val result: List<Location>,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<List<Location>, JsonElement> {
    companion object : TypedResponseObjectConverter<List<Location>, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<List<Location>, JsonElement> {
            return ReferencesResponse(
                id = response.id,
                result = response.result?.let { LocationArray.deserialize(it) } ?: listOf(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.references(handler: ReferencesParams.() -> List<Location>) {
    if (request.method == TextDocumentRequest.REFERENCES) {
        val result = request.params(ReferencesParams).handler()
        request.sendResult(result, LocationArray)
    }
}

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.references(
    params: ReferencesParams,
    responseHandler: (TypedResponseObject<List<Location>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.REFERENCES,
    params = ReferencesParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ReferencesResponse
)

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * @param uri the text document's URI
 * @param position the position inside the text document
 * @param context the reference context
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.references(
    uri: String,
    position: Position,
    context: ReferenceContext,
    responseHandler: (TypedResponseObject<List<Location>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = references(
    params = ReferencesParams(
        uri = uri,
        position = position,
        context = context
    ),
    responseHandler = responseHandler
)
