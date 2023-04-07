// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.WorkspaceEdit
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Parameters for `textDocument/rename` request.
 *
 * @since 1.0.0
 */
data class RenameParams(
    /**
     * The document containing the symbol to rename.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The position at which this request was send.
     */
    val position: Position,

    /**
     * The new name of the symbol.
     *
     * If the given name is not valid the request must return a `ResponseError`
     * with an appropriate message set.
     */
    val newName: String
) {
    companion object : JsonSerialization<RenameParams> {
        override fun serializeToJson(value: RenameParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("position", value.position, Position)
            put("newName", value.newName, JsonString)
        }

        override fun deserialize(json: JsonElement): RenameParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> RenameParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                position = json.get("position", Position),
                newName = json.get("newName", JsonString)
            )
        }
    }
}

/**
 * The response of a rename request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class RenameResponse(
    override val id: JsonIntOrString?,
    override val result: WorkspaceEdit?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<WorkspaceEdit?, JsonElement> {
    companion object : TypedResponseObjectConverter<WorkspaceEdit?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<WorkspaceEdit?, JsonElement> {
            return RenameResponse(
                id = response.id,
                result = response.result?.let { WorkspaceEdit.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The rename request is sent from the client to the server to do a workspace wide rename
 * of a symbol.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.rename(handler: RenameParams.() -> WorkspaceEdit) {
    if (request.method == TextDocumentRequest.RENAME) {
        val result = request.params(RenameParams).handler()
        request.sendResult(result, WorkspaceEdit)
    }
}

/**
 * The rename request is sent from the client to the server to do a workspace wide rename
 * of a symbol.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.rename(
    params: RenameParams,
    responseHandler: (TypedResponseObject<WorkspaceEdit?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.RENAME,
    params = RenameParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = RenameResponse
)

/**
 * The rename request is sent from the client to the server to do a workspace wide rename
 * of a symbol.
 *
 * @param textDocument the document containing the symbol to rename
 * @param position the position inside the text document
 * @param newName the new name of the symbol
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.rename(
    textDocument: TextDocumentIdentifier,
    position: Position,
    newName: String,
    responseHandler: (TypedResponseObject<WorkspaceEdit?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = rename(
    params = RenameParams(
        textDocument = textDocument,
        position = position,
        newName = newName
    ),
    responseHandler = responseHandler
)
