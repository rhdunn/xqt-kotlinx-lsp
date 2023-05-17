// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextDocumentPositionParams
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Represents a goto response result object.
 *
 * NOTE: This is an LSP extension designed to encapsulate the different result
 * types of the Goto Definition response.
 *
 * @since 1.0.0
 */
data class GoTo(
    /**
     * The locations of the definition to go to.
     */
    val locations: List<Location> = listOf()
) : List<Location> by locations {
    constructor(location: Location) : this(listOf(location))
    constructor(vararg locations: Location) : this(listOf(*locations))

    /**
     * The location of the definition to go to.
     *
     * If there are more than one location to go to, this is the first location
     * in the list.
     */
    val location: Location?
        get() = locations.firstOrNull()

    companion object : JsonSerialization<GoTo> {
        private val LocationArray = JsonTypedArray(Location)

        override fun serializeToJson(value: GoTo): JsonElement = when (value.locations.size) {
            1 -> Location.serializeToJson(value.locations[0])
            else -> LocationArray.serializeToJson(value.locations)
        }

        override fun deserialize(json: JsonElement): GoTo = when (json) {
            is JsonObject -> GoTo(Location.deserialize(json))
            is JsonArray -> GoTo(LocationArray.deserialize(json))
            else -> unsupportedKindType(json)
        }
    }
}

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
data class GoToResponse(
    override val id: JsonIntOrString?,
    override val result: GoTo,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<GoTo, JsonElement> {
    companion object : TypedResponseObjectConverter<GoTo, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<GoTo, JsonElement> {
            return GoToResponse(
                id = response.id,
                result = response.result?.let { GoTo.deserialize(it) } ?: GoTo(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The goto definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @since 2.0.0
 */
fun TextDocumentRequest.definition(
    handler: TextDocumentPositionParams.() -> GoTo
): Unit = request.method(
    method = TextDocumentRequest.DEFINITION,
    handler = handler,
    paramsSerializer = TextDocumentPositionParams,
    resultSerializer = GoTo
)

/**
 * The goto definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.definition(
    params: TextDocumentPositionParams,
    responseHandler: (TypedResponseObject<GoTo, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.DEFINITION,
    params = TextDocumentPositionParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = GoToResponse
)

/**
 * The goto definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
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
fun TextDocumentJsonRpcServer.definition(
    textDocument: TextDocumentIdentifier,
    position: Position,
    responseHandler: (TypedResponseObject<GoTo, JsonElement>.() -> Unit)? = null
): JsonIntOrString = definition(
    params = TextDocumentPositionParams(
        textDocument = textDocument,
        position = position
    ),
    responseHandler = responseHandler
)
