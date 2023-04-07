// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Parameters for `textDocument/didChange` notification.
 *
 * @since 1.0.0
 */
data class DidChangeTextDocumentParams(
    override val uri: String,

    /**
     * The actual content changes.
     */
    val contentChanges: List<TextDocumentContentChangeEvent>
) : TextDocumentIdentifier {
    companion object : JsonSerialization<DidChangeTextDocumentParams> {
        private val TextDocumentContentChangeEventArray = JsonTypedArray(TextDocumentContentChangeEvent)

        override fun serializeToJson(value: DidChangeTextDocumentParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("contentChanges", value.contentChanges, TextDocumentContentChangeEventArray)
        }

        override fun deserialize(json: JsonElement): DidChangeTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidChangeTextDocumentParams(
                uri = json.get("uri", JsonString),
                contentChanges = json.get("contentChanges", TextDocumentContentChangeEventArray)
            )
        }
    }
}

/**
 * An event describing a change to a text document.
 *
 * If range and rangeLength are omitted the new text is considered to be the
 * full content of the document.
 *
 * @since 1.0.0
 */
data class TextDocumentContentChangeEvent(
    /**
     * The range of the document that changed.
     */
    val range: Range? = null,

    /**
     * The length of the range that got replaced.
     */
    val rangeLength: UInt? = null,

    /**
     * The new text of the document.
     */
    val text: String
) {
    companion object : JsonSerialization<TextDocumentContentChangeEvent> {
        override fun serializeToJson(value: TextDocumentContentChangeEvent): JsonObject = buildJsonObject {
            putOptional("range", value.range, Range)
            putOptional("rangeLength", value.rangeLength, UInteger)
            put("text", value.text, JsonString)
        }

        override fun deserialize(json: JsonElement): TextDocumentContentChangeEvent = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextDocumentContentChangeEvent(
                range = json.getOptional("range", Range),
                rangeLength = json.getOptional("rangeLength", UInteger),
                text = json.get("text", JsonString)
            )
        }
    }
}

/**
 * The document change notification is sent from the client to the server to signal changes
 * to a text document.
 *
 * @since 1.0.0
 */
fun TextDocumentNotification.didChange(handler: DidChangeTextDocumentParams.() -> Unit) {
    if (notification.method == TextDocumentNotification.DID_CHANGE) {
        notification.params(DidChangeTextDocumentParams).handler()
    }
}

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
fun TextDocumentJsonRpcServer.didChange(
    params: DidChangeTextDocumentParams
): Unit = server.sendNotification(
    method = TextDocumentNotification.DID_CHANGE,
    params = DidChangeTextDocumentParams.serializeToJson(params)
)

/**
 * The document open notification is sent from the client to the server to signal newly
 * opened text documents.
 *
 * The document's content is now managed by the client and the server must not try to read
 * the document's content using the document's uri.
 *
 * @param uri the text document's URI
 * @param contentChanges the actual content changes
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.didChange(
    uri: String,
    contentChanges: List<TextDocumentContentChangeEvent>
): Unit = didChange(
    params = DidChangeTextDocumentParams(uri = uri, contentChanges = contentChanges)
)
