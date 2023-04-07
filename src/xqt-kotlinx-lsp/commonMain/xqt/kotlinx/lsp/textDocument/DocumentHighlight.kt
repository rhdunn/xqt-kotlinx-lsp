// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import kotlin.jvm.JvmInline

/**
 * A document highlight is a range inside a text document which deserves
 * special attention.
 *
 * Usually a document highlight is visualized by changing the background
 * color of its range.
 *
 * @since 1.0.0
 */
data class DocumentHighlight(
    /**
     * The range this highlight applies to.
     */
    val range: Range,

    /**
     * The highlight kind.
     *
     * The default is `DocumentHighlightKind.Text`.
     */
    val kind: DocumentHighlightKind? = null
) {
    companion object : JsonSerialization<DocumentHighlight> {
        override fun serializeToJson(value: DocumentHighlight): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            putOptional("kind", value.kind, DocumentHighlightKind)
        }

        override fun deserialize(json: JsonElement): DocumentHighlight = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentHighlight(
                range = json.get("range", Range),
                kind = json.getOptional("kind", DocumentHighlightKind)
            )
        }
    }
}

/**
 * A document highlight kind.
 *
 * @param kind the document highlight kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class DocumentHighlightKind(val kind: Int) {
    companion object : JsonSerialization<DocumentHighlightKind> {
        override fun serializeToJson(value: DocumentHighlightKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): DocumentHighlightKind {
            return DocumentHighlightKind(JsonInt.deserialize(json))
        }

        /**
         * A textual occurrence.
         */
        val Text: DocumentHighlightKind = DocumentHighlightKind(1)

        /**
         * Read-access of a symbol, like reading a variable.
         */
        val Read: DocumentHighlightKind = DocumentHighlightKind(2)

        /**
         * Write-access of a symbol, like writing to a variable.
         */
        val Write: DocumentHighlightKind = DocumentHighlightKind(3)
    }
}

/**
 * The response of a document highlight request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class DocumentHighlightResponse(
    override val id: JsonIntOrString?,
    override val result: DocumentHighlight?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<DocumentHighlight?, JsonElement> {
    companion object : TypedResponseObjectConverter<DocumentHighlight?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<DocumentHighlight?, JsonElement> {
            return DocumentHighlightResponse(
                id = response.id,
                result = response.result?.let { DocumentHighlight.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The document highlight request is sent from the client to the server to resolve the document
 * highlights for a given text document position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.documentHighlight(
    handler: TextDocumentPosition.() -> DocumentHighlight
): Unit = request.method(
    method = TextDocumentRequest.DOCUMENT_HIGHLIGHT,
    handler = handler,
    paramsSerializer = TextDocumentPosition,
    resultSerializer = DocumentHighlight
)

/**
 * The document highlight request is sent from the client to the server to resolve the document
 * highlights for a given text document position.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.documentHighlight(
    params: TextDocumentPosition,
    responseHandler: (TypedResponseObject<DocumentHighlight?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.DOCUMENT_HIGHLIGHT,
    params = TextDocumentPosition.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = DocumentHighlightResponse
)

/**
 * The document highlight request is sent from the client to the server to resolve the document
 * highlights for a given text document position.
 *
 * @param uri the text document's URI
 * @param position the position inside the text document
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.documentHighlight(
    uri: String,
    position: Position,
    responseHandler: (TypedResponseObject<DocumentHighlight?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = documentHighlight(
    params = TextDocumentPosition(uri = uri, position = position),
    responseHandler = responseHandler
)
