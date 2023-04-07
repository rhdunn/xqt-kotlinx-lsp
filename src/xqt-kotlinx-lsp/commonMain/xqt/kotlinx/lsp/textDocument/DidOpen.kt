// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Parameters for `textDocument/didOpen` notification.
 *
 * @since 1.0.0
 */
data class DidOpenTextDocumentParams(
    override val uri: String,

    /**
     * The content of the opened text document.
     */
    val text: String
) : TextDocumentIdentifier {
    companion object : JsonSerialization<DidOpenTextDocumentParams> {
        override fun serializeToJson(value: DidOpenTextDocumentParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("text", value.text, JsonString)
        }

        override fun deserialize(json: JsonElement): DidOpenTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidOpenTextDocumentParams(
                uri = json.get("uri", JsonString),
                text = json.get("text", JsonString)
            )
        }
    }
}

/**
 * The document open notification is sent from the client to the server to signal newly
 * opened text documents.
 *
 * The document's content is now managed by the client and the server must not try to read
 * the document's content using the document's uri.
 *
 * @since 1.0.0
 */
fun TextDocumentNotification.didOpen(
    handler: DidOpenTextDocumentParams.() -> Unit
): Unit = notification.method(
    method = TextDocumentNotification.DID_OPEN,
    handler = handler,
    paramsSerializer = DidOpenTextDocumentParams
)

/**
 * The document open notification is sent from the client to the server to signal newly
 * opened text documents.
 *
 * The document's content is now managed by the client and the server must not try to read
 * the document's content using the document's uri.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.didOpen(
    params: DidOpenTextDocumentParams
): Unit = server.sendNotification(
    method = TextDocumentNotification.DID_OPEN,
    params = DidOpenTextDocumentParams.serializeToJson(params)
)

/**
 * The document open notification is sent from the client to the server to signal newly
 * opened text documents.
 *
 * The document's content is now managed by the client and the server must not try to read
 * the document's content using the document's uri.
 *
 * @param uri the text document's URI
 * @param text the content of the opened text document
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.didOpen(
    uri: String,
    text: String
): Unit = didOpen(
    params = DidOpenTextDocumentParams(uri = uri, text = text)
)
