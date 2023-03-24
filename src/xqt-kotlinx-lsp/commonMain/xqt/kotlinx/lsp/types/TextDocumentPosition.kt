// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Identifies a position in a text document.
 *
 * @since 1.0.0
 */
interface TextDocumentPosition : TextDocumentIdentifier {
    override val uri: String

    /**
     * The position inside the text document.
     */
    val position: Position

    companion object : JsonSerialization<TextDocumentPosition> {
        override fun serializeToJson(value: TextDocumentPosition): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("position", value.position, Position)
        }

        override fun deserialize(json: JsonElement): TextDocumentPosition = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextDocumentPosition(
                uri = json.get("uri", JsonString),
                position = json.get("position", Position)
            )
        }
    }
}

/**
 * Identifies a position in a text document.
 *
 * @param uri the text document's URI
 * @param position the position inside the text document
 *
 * @since 1.0.0
 */
fun TextDocumentPosition(
    uri: String,
    position: Position
): TextDocumentPosition = object : TextDocumentPosition {
    override val uri: String = uri
    override val position: Position = position
}
