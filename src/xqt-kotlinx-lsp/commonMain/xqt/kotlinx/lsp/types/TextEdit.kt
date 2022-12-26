// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * A textual edit applicable to a text document.
 *
 * @since 1.0.0
 */
data class TextEdit(
    /**
     * The range of the text document to be manipulated.
     *
     * To insert text into a document create a range where start === end.
     */
    val range: Range,

    /**
     * The string to be inserted.
     *
     * For delete operations use an empty string.
     */
    val newText: String
) {
    companion object : JsonSerialization<TextEdit> {
        override fun serializeToJson(value: TextEdit): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            put("newText", value.newText, JsonString)
        }

        override fun deserialize(json: JsonElement): TextEdit = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextEdit(
                range = json.get("range", Range),
                newText = json.get("newText", JsonString)
            )
        }
    }
}
