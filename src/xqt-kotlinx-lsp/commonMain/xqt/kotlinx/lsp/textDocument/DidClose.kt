// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

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
    handler: TextDocumentIdentifier.() -> Unit
): Unit = notification.method(
    method = TextDocumentNotification.DID_CLOSE,
    handler = handler,
    paramsSerializer = TextDocumentIdentifier
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
    params: TextDocumentIdentifier
): Unit = server.sendNotification(
    method = TextDocumentNotification.DID_CLOSE,
    params = TextDocumentIdentifier.serializeToJson(params)
)

/**
 * The document close notification is sent from the client to the server when the document
 * got closed in the client.
 *
 * The document's master now exists where the document's URI points to (e.g. if the document's
 * URI is a file uri the master now exists on disk).
 *
 * @param uri the text document's URI
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.didClose(
    uri: String
): Unit = didClose(
    params = TextDocumentIdentifier(uri = uri)
)
