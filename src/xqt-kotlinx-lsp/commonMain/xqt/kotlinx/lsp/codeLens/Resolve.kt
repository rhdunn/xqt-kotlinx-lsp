// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.codeLens

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.lsp.textDocument.CodeLens
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString

/**
 * The response of a code lens request.
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
    override val result: CodeLens?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<CodeLens?, JsonElement> {
    companion object : TypedResponseObjectConverter<CodeLens?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<CodeLens?, JsonElement> {
            return ResolveResponse(
                id = response.id,
                result = response.result?.let { CodeLens.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The code lens resolve request is sent from the client to the server to resolve the
 * command for a given code lens item.
 *
 * @since 1.0.0
 */
fun CodeLensRequest.resolve(handler: CodeLens.() -> CodeLens) {
    if (request.method == CodeLensRequest.RESOLVE) {
        val result = request.params(CodeLens).handler()
        request.sendResult(result, CodeLens)
    }
}

/**
 * The code lens resolve request is sent from the client to the server to resolve the
 * command for a given code lens item.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun CodeLensJsonRpcServer.resolve(
    params: CodeLens,
    responseHandler: (TypedResponseObject<CodeLens?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = CodeLensRequest.RESOLVE,
    params = CodeLens.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ResolveResponse
)
