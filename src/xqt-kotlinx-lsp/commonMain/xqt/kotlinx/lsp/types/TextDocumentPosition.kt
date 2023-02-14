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
            else -> TextDocumentPositionImpl(
                uri = json.get("uri", JsonString),
                position = json.get("position", Position)
            )
        }
    }
}

internal data class TextDocumentPositionImpl(
    override val uri: String,
    override val position: Position
) : TextDocumentPosition
