// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentItem
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Parameters for `textDocument/didOpen` notification.
 *
 * @since 1.0.0
 */
data class DidOpenTextDocumentParams(
    /**
     * The document that was opened.
     *
     * This is a combination of the `uri` and `text` properties from LSP 1.x in
     * addition to other new properties.
     *
     * @since 2.0.0
     */
    val textDocument: TextDocumentItem
) {
    companion object : JsonSerialization<DidOpenTextDocumentParams> {
        override fun serializeToJson(value: DidOpenTextDocumentParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentItem)
        }

        override fun deserialize(json: JsonElement): DidOpenTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidOpenTextDocumentParams(
                textDocument = json.get("textDocument", TextDocumentItem)
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
 * @param textDocument the document that was opened
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.didOpen(
    textDocument: TextDocumentItem
): Unit = didOpen(
    params = DidOpenTextDocumentParams(textDocument = textDocument)
)
