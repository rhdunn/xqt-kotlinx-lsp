// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedObjectOrArray

/**
 * The `textDocument/hover` request response.
 *
 * @since 1.0.0
 */
data class Hover(
    /**
     * The hover's content.
     */
    val contents: List<MarkedString>,

    /**
     * An optional range.
     */
    val range: Range? = null
) {
    companion object : JsonSerialization<Hover> {
        private val MarkedStringArray = JsonTypedObjectOrArray(MarkedString)

        override fun serializeToJson(value: Hover): JsonObject = buildJsonObject {
            put("contents", value.contents, MarkedStringArray)
            putOptional("range", value.range, Range)
        }

        override fun deserialize(json: JsonElement): Hover = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Hover(
                contents = json.get("contents", MarkedStringArray),
                range = json.getOptional("range", Range)
            )
        }
    }
}

/**
 * Render human-readable text or code-block.
 *
 * @since 1.0.0
 */
data class MarkedString(
    /**
     * The language of the markdown code-block.
     */
    val language: String? = null,

    /**
     * The contents of the markdown or code-block.
     */
    val value: String
) {
    companion object : JsonSerialization<MarkedString> {
        override fun serializeToJson(value: MarkedString): JsonElement = when (value.language) {
            null -> JsonPrimitive(value.value)
            else -> buildJsonObject {
                put("language", value.language, JsonString)
                put("value", value.value, JsonString)
            }
        }

        override fun deserialize(json: JsonElement): MarkedString = when (json) {
            is JsonPrimitive -> when (json.kindType) {
                KindType.String -> MarkedString(value = json.content)
                else -> unsupportedKindType(json)
            }

            is JsonObject -> MarkedString(
                language = json.get("language", JsonString),
                value = json.get("value", JsonString)
            )

            else -> unsupportedKindType(json)
        }
    }
}
