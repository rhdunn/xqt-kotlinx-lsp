// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextDocumentPositionParams
import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonIntEnumerationType
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
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
value class DocumentHighlightKind(override val kind: Int) : JsonEnumeration<Int> {
    companion object : JsonIntEnumerationType<DocumentHighlightKind>() {
        override fun valueOf(value: Int): DocumentHighlightKind = DocumentHighlightKind(value)

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

private val DocumentHighlightArray = JsonTypedArray(DocumentHighlight)

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
    override val result: List<DocumentHighlight>,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<List<DocumentHighlight>, JsonElement> {
    companion object : TypedResponseObjectConverter<List<DocumentHighlight>, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<List<DocumentHighlight>, JsonElement> {
            return DocumentHighlightResponse(
                id = response.id,
                result = response.result?.let { DocumentHighlightArray.deserialize(it) } ?: listOf(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The document highlight request is sent from the client to the server to resolve document
 * highlights for a given text document position.
 *
 * For programming languages this usually highlights all references to the symbol scoped to
 * this file. However, we kept `textDocument/documentHighlight` and `textDocument/references`
 * separate requests since the first one is allowed to be more fuzzy. Symbol matches usually
 * have a `DocumentHighlightKind` of `Read` or `Write` whereas fuzzy or textual matches use
 * `Text` as the kind.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * __NOTE:__ In LSP 1.x, the result type is a single `DocumentHighlight` object.
 *
 * @since 2.0.0
 */
fun TextDocumentRequest.documentHighlight(
    handler: TextDocumentPositionParams.() -> List<DocumentHighlight>
): Unit = request.method(
    method = TextDocumentRequest.DOCUMENT_HIGHLIGHT,
    handler = handler,
    paramsSerializer = TextDocumentPositionParams,
    resultSerializer = DocumentHighlightArray
)

/**
 * The document highlight request is sent from the client to the server to resolve document
 * highlights for a given text document position.
 *
 * For programming languages this usually highlights all references to the symbol scoped to
 * this file. However, we kept `textDocument/documentHighlight` and `textDocument/references`
 * separate requests since the first one is allowed to be more fuzzy. Symbol matches usually
 * have a `DocumentHighlightKind` of `Read` or `Write` whereas fuzzy or textual matches use
 * `Text` as the kind.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * __NOTE:__ In LSP 1.x, the result type is a single `DocumentHighlight` object.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.documentHighlight(
    params: TextDocumentPositionParams,
    responseHandler: (TypedResponseObject<List<DocumentHighlight>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.DOCUMENT_HIGHLIGHT,
    params = TextDocumentPositionParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = DocumentHighlightResponse
)

/**
 * The document highlight request is sent from the client to the server to resolve document
 * highlights for a given text document position.
 *
 * For programming languages this usually highlights all references to the symbol scoped to
 * this file. However, we kept `textDocument/documentHighlight` and `textDocument/references`
 * separate requests since the first one is allowed to be more fuzzy. Symbol matches usually
 * have a `DocumentHighlightKind` of `Read` or `Write` whereas fuzzy or textual matches use
 * `Text` as the kind.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * __NOTE:__ In LSP 1.x, the result type is a single `DocumentHighlight` object.
 *
 * @param textDocument the text document
 * @param position the position inside the text document
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.documentHighlight(
    textDocument: TextDocumentIdentifier,
    position: Position,
    responseHandler: (TypedResponseObject<List<DocumentHighlight>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = documentHighlight(
    params = TextDocumentPositionParams(
        textDocument = textDocument,
        position = position
    ),
    responseHandler = responseHandler
)
