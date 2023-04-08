// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Position in a text document expressed as zero-based line and zero-based
 * character offset.
 *
 * A position is between two characters like an 'insert' cursor in a editor.
 *
 * @since 1.0.0
 */
data class Position(
    /**
     * Line position in a document (zero-based).
     */
    val line: UInt,

    /**
     * Character offset on a line in a document (zero-based).
     */
    val character: UInt
) {
    companion object : JsonSerialization<Position> {
        override fun serializeToJson(value: Position): JsonObject = buildJsonObject {
            put("line", value.line, UInteger)
            put("character", value.character, UInteger)
        }

        override fun deserialize(json: JsonElement): Position = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Position(
                line = json.get("line", UInteger),
                character = json.get("character", UInteger)
            )
        }
    }
}
