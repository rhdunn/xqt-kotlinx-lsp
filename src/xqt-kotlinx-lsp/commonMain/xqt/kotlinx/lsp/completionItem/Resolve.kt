// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.completionItem

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString

/**
 * The response of a resolve request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class ResolveResponse(
    override val id: JsonIntOrString?,
    override val result: CompletionItem?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<CompletionItem?, JsonElement> {
    companion object : TypedResponseObjectConverter<CompletionItem?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<CompletionItem?, JsonElement> {
            return ResolveResponse(
                id = response.id,
                result = response.result?.let { CompletionItem.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The request is sent from the client to the server to resolve additional information for
 * a given completion item.
 *
 * @since 1.0.0
 */
fun CompletionItemRequest.resolve(
    handler: CompletionItem.() -> CompletionItem
): Unit = request.method(
    method = CompletionItemRequest.RESOLVE,
    handler = handler,
    paramsSerializer = CompletionItem,
    resultSerializer = CompletionItem
)

/**
 * The request is sent from the client to the server to resolve additional information for
 * a given completion item.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun CompletionItemJsonRpcServer.resolve(
    params: CompletionItem,
    responseHandler: (TypedResponseObject<CompletionItem?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = CompletionItemRequest.RESOLVE,
    params = CompletionItem.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ResolveResponse
)
