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
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * The `textDocument/didClose` request parameters.
 *
 * @since 2.0.0
 */
data class DidCloseTextDocumentParams(
    /**
     * The document that was closed.
     */
    val textDocument: TextDocumentIdentifier
) {
    companion object : JsonSerialization<DidCloseTextDocumentParams> {
        override fun serializeToJson(value: DidCloseTextDocumentParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
        }

        override fun deserialize(json: JsonElement): DidCloseTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidCloseTextDocumentParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier)
            )
        }
    }
}

/**
 * The document close notification is sent from the client to the server when the document
 * got closed in the client.
 *
 * The document's master now exists where the document's URI points to (e.g. if the document's
 * URI is a file uri the master now exists on disk).
 *
 * @since 1.0.0
 */
fun TextDocumentNotification.didClose(
    handler: DidCloseTextDocumentParams.() -> Unit
): Unit = notification.method(
    method = TextDocumentNotification.DID_CLOSE,
    handler = handler,
    paramsSerializer = DidCloseTextDocumentParams
)

/**
 * The document close notification is sent from the client to the server when the document
 * got closed in the client.
 *
 * The document's master now exists where the document's URI points to (e.g. if the document's
 * URI is a file uri the master now exists on disk).
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.didClose(
    params: DidCloseTextDocumentParams
): Unit = server.sendNotification(
    method = TextDocumentNotification.DID_CLOSE,
    params = DidCloseTextDocumentParams.serializeToJson(params)
)

/**
 * The document close notification is sent from the client to the server when the document
 * got closed in the client.
 *
 * The document's master now exists where the document's URI points to (e.g. if the document's
 * URI is a file uri the master now exists on disk).
 *
 * @param textDocument the document that was closed
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.didClose(
    textDocument: TextDocumentIdentifier
): Unit = didClose(
    params = DidCloseTextDocumentParams(textDocument = textDocument)
)
