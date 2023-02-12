// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

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
