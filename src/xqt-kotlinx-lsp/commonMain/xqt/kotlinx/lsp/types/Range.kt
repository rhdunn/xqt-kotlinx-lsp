// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * A range in a text document expressed as (zero-based) start and end positions.
 *
 * A range is comparable to a selection in an editor. Therefore, the end position is exclusive.
 *
 * @since 1.0.0
 */
data class Range(
    /**
     * The range's start position.
     */
    val start: Position,

    /**
     * The range's end position.
     */
    val end: Position
) {
    companion object : JsonSerialization<Range> {
        override fun serializeToJson(value: Range): JsonObject = buildJsonObject {
            put("start", value.start, Position)
            put("end", value.end, Position)
        }

        override fun deserialize(json: JsonElement): Range = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Range(
                start = json.get("start", Position),
                end = json.get("end", Position)
            )
        }
    }
}
