// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.Integer
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * An item to transfer a text document from the client to the server.
 *
 * @since 2.0.0
 */
data class TextDocumentItem(
    /**
     * The text document's URI.
     */
    val uri: String,

    /**
     * The text document's language identifier.
     */
    val languageId: String,

    /**
     * The version number of this document (it will strictly increase after each
     * change, including undo/redo).
     */
    val version: Int,

    /**
     * The content of the opened text document.
     */
    val text: String
) {
    companion object : JsonSerialization<TextDocumentItem> {
        override fun serializeToJson(value: TextDocumentItem): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("languageId", value.languageId, JsonString)
            put("version", value.version, Integer)
            put("text", value.text, JsonString)
        }

        override fun deserialize(json: JsonElement): TextDocumentItem = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextDocumentItem(
                uri = json.get("uri", JsonString),
                languageId = json.get("languageId", JsonString),
                version = json.get("version", Integer),
                text = json.get("text", JsonString)
            )
        }
    }
}
