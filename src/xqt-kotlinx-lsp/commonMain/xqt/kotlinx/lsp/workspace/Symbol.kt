// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.textDocument.SymbolInformation
import xqt.kotlinx.lsp.textDocument.SymbolInformationResponse
import xqt.kotlinx.rpc.json.protocol.TypedResponseObject
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendRequest
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Capabilities specific to the `workspace/symbol` request.
 *
 * @since 3.0.0
 */
data class WorkspaceSymbolClientCapabilities(
    /**
     * Symbol request supports dynamic registration.
     */
    val dynamicRegistration: Boolean? = null
) {
    companion object : JsonSerialization<WorkspaceSymbolClientCapabilities> {
        override fun serializeToJson(value: WorkspaceSymbolClientCapabilities): JsonObject = buildJsonObject {
            putOptional("dynamicRegistration", value.dynamicRegistration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): WorkspaceSymbolClientCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> WorkspaceSymbolClientCapabilities(
                dynamicRegistration = json.getOptional("dynamicRegistration", JsonBoolean)
            )
        }
    }
}

/**
 * Parameters for `workspace/symbol` request.
 *
 * @since 1.0.0
 */
data class WorkspaceSymbolParams(
    /**
     * A non-empty query string.
     */
    val query: String
) {
    companion object : JsonSerialization<WorkspaceSymbolParams> {
        override fun serializeToJson(value: WorkspaceSymbolParams): JsonObject = buildJsonObject {
            put("query", value.query, JsonString)
        }

        override fun deserialize(json: JsonElement): WorkspaceSymbolParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> WorkspaceSymbolParams(
                query = json.get("query", JsonString)
            )
        }
    }
}

private val SymbolInformationArray = JsonTypedArray(SymbolInformation)

/**
 * The workspace symbol request is sent from the client to the server to list project-wide
 * symbols matching the query string.
 *
 * @since 1.0.0
 */
fun WorkspaceRequest.symbol(
    handler: WorkspaceSymbolParams.() -> List<SymbolInformation>
): Unit = request.method(
    method = WorkspaceRequest.SYMBOL,
    handler = handler,
    paramsSerializer = WorkspaceSymbolParams,
    resultSerializer = SymbolInformationArray
)

/**
 * The workspace symbol request is sent from the client to the server to list project-wide
 * symbols matching the query string.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.symbol(
    params: WorkspaceSymbolParams,
    responseHandler: (TypedResponseObject<List<SymbolInformation>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = WorkspaceRequest.SYMBOL,
    params = WorkspaceSymbolParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = SymbolInformationResponse
)

/**
 * The workspace symbol request is sent from the client to the server to list project-wide
 * symbols matching the query string.
 *
 * @param query a non-empty query string
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.symbol(
    query: String,
    responseHandler: (TypedResponseObject<List<SymbolInformation>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = symbol(
    params = WorkspaceSymbolParams(query = query),
    responseHandler = responseHandler
)
