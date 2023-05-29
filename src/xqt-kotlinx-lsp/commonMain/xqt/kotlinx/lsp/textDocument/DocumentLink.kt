// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.*
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Parameters for `textDocument/documentLink` request.
 *
 * @since 2.0.0
 */
data class DocumentLinkParams(
    /**
     * The document to provide document links for.
     */
    val textDocument: TextDocumentIdentifier
) {
    companion object : JsonSerialization<DocumentLinkParams> {
        override fun serializeToJson(value: DocumentLinkParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
        }

        override fun deserialize(json: JsonElement): DocumentLinkParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentLinkParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier)
            )
        }
    }
}

/**
 * A document link is a range in a text document that links to an internal or
 * external resource, like another text document or a website.
 *
 * @since 2.0.0
 */
data class DocumentLink(
    /**
     * The range this link applies to.
     */
    val range: Range,

    /**
     * The uri this link points to.
     */
    val target: String
) {
    companion object : JsonSerialization<DocumentLink> {
        override fun serializeToJson(value: DocumentLink): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            put("target", value.target, JsonString)
        }

        override fun deserialize(json: JsonElement): DocumentLink = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentLink(
                range = json.get("range", Range),
                target = json.get("target", JsonString)
            )
        }
    }
}

private object DocumentLinkResult : JsonSerialization<List<DocumentLink>> {
    override fun serializeToJson(value: List<DocumentLink>): JsonElement = when (value.size) {
        0 -> JsonNull
        else -> buildJsonArray {
            value.forEach { item -> add(DocumentLink.serializeToJson(item)) }
        }
    }

    override fun deserialize(json: JsonElement): List<DocumentLink> = when (json) {
        is JsonNull -> listOf()
        is JsonArray -> json.map { item -> DocumentLink.deserialize(item) }
        else -> unsupportedKindType(json)
    }
}

/**
 * The response of a code lens request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 2.0.0
 */
data class DocumentLinkResponse(
    override val id: JsonIntOrString?,
    override val result: List<DocumentLink>,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<List<DocumentLink>, JsonElement> {
    companion object : TypedResponseObjectConverter<List<DocumentLink>, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<List<DocumentLink>, JsonElement> {
            return DocumentLinkResponse(
                id = response.id,
                result = response.result?.let { DocumentLinkResult.deserialize(it) } ?: listOf(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The document links request is sent from the client to the server to request the location
 * of links in a document.
 *
 * @since 2.0.0
 */
fun TextDocumentRequest.documentLink(
    handler: DocumentLinkParams.() -> List<DocumentLink>
): Unit = request.method(
    method = TextDocumentRequest.DOCUMENT_LINK,
    handler = handler,
    paramsSerializer = DocumentLinkParams,
    resultSerializer = DocumentLinkResult
)

/**
 * The document links request is sent from the client to the server to request the location
 * of links in a document.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.documentLink(
    params: DocumentLinkParams,
    responseHandler: (TypedResponseObject<List<DocumentLink>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.DOCUMENT_LINK,
    params = DocumentLinkParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = DocumentLinkResponse
)

/**
 * The document links request is sent from the client to the server to request the location
 * of links in a document.
 *
 * @param textDocument the document to provide document links for
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.documentLink(
    textDocument: TextDocumentIdentifier,
    responseHandler: (TypedResponseObject<List<DocumentLink>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = documentLink(
    params = DocumentLinkParams(textDocument = textDocument),
    responseHandler = responseHandler
)
