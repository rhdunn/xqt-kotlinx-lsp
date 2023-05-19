// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.documentLink

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.lsp.completionItem.CompletionItemJsonRpcServer
import xqt.kotlinx.lsp.completionItem.CompletionItemRequest
import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.lsp.textDocument.DocumentLink
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
 * @since 2.0.0
 */
data class ResolveResponse(
    override val id: JsonIntOrString?,
    override val result: DocumentLink?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<DocumentLink?, JsonElement> {
    companion object : TypedResponseObjectConverter<DocumentLink?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<DocumentLink?, JsonElement> {
            return ResolveResponse(
                id = response.id,
                result = response.result?.let { DocumentLink.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The document link resolve request is sent from the client to the server
 * to resolve the target of a given document link.
 *
 * @since 2.0.0
 */
fun DocumentLinkRequest.resolve(
    handler: DocumentLink.() -> DocumentLink
): Unit = request.method(
    method = DocumentLinkRequest.RESOLVE,
    handler = handler,
    paramsSerializer = DocumentLink,
    resultSerializer = DocumentLink
)

/**
 * The document link resolve request is sent from the client to the server
 * to resolve the target of a given document link.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun DocumentLinkJsonRpcServer.resolve(
    params: DocumentLink,
    responseHandler: (TypedResponseObject<DocumentLink?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = DocumentLinkRequest.RESOLVE,
    params = DocumentLink.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ResolveResponse
)
