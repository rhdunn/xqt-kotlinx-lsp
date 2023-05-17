// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextDocumentPositionParams
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * The `textDocument/references` request parameters.
 *
 * @since 1.0.0
 */
data class ReferenceParams(
    override val position: Position,

    /**
     * The text document.
     *
     * __NOTE:__ In LSP 1.x, this was an inlined `uri` parameter.
     *
     * @since 2.0.0
     */
    override val textDocument: TextDocumentIdentifier,

    /**
     * The reference context.
     */
    val context: ReferenceContext
) : TextDocumentPositionParams {
    companion object : JsonSerialization<ReferenceParams> {
        override fun serializeToJson(value: ReferenceParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("position", value.position, Position)
            put("context", value.context, ReferenceContext)
        }

        override fun deserialize(json: JsonElement): ReferenceParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferenceParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
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
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.references(
    handler: ReferenceParams.() -> List<Location>
): Unit = request.method(
    method = TextDocumentRequest.REFERENCES,
    handler = handler,
    paramsSerializer = ReferenceParams,
    resultSerializer = LocationArray
)

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.references(
    params: ReferenceParams,
    responseHandler: (TypedResponseObject<List<Location>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.REFERENCES,
    params = ReferenceParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ReferencesResponse
)

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param textDocument the text document
 * @param position the position inside the text document
 * @param context the reference context
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.references(
    textDocument: TextDocumentIdentifier,
    position: Position,
    context: ReferenceContext,
    responseHandler: (TypedResponseObject<List<Location>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = references(
    params = ReferenceParams(
        textDocument = textDocument,
        position = position,
        context = context
    ),
    responseHandler = responseHandler
)
