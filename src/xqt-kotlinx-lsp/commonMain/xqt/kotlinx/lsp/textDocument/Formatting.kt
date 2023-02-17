// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonPrimitiveValue

/**
 * The `textDocument/formatting` request parameters.
 *
 * @since 1.0.0
 */
data class DocumentFormattingParams(
    /**
     * The document to format.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The format options.
     */
    val options: FormattingOptions
) {
    companion object : JsonSerialization<DocumentFormattingParams> {
        override fun serializeToJson(value: DocumentFormattingParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("options", value.options, FormattingOptions)
        }

        override fun deserialize(json: JsonElement): DocumentFormattingParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentFormattingParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                options = json.get("options", FormattingOptions)
            )
        }
    }
}

/**
 * Value-object describing what options formatting should use.
 *
 * @since 1.0.0
 */
data class FormattingOptions(
    /**
     * Size of a tab in spaces.
     */
    val tabSize: UInt,

    /**
     * Prefer using spaces over tabs.
     */
    val insertSpace: Boolean,

    /**
     * Signature for further options.
     *
     * In this implementation all options are included in the map.
     */
    private val options: Map<String, JsonPrimitive>
) : Map<String, JsonPrimitive> by options {
    companion object : JsonSerialization<FormattingOptions> {
        override fun serializeToJson(value: FormattingOptions): JsonObject = JsonObject(value.options)

        override fun deserialize(json: JsonElement): FormattingOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> FormattingOptions(
                tabSize = json.get("tabSize", UInteger),
                insertSpace = json.get("insertSpace", JsonBoolean),
                options = json.mapValues { (_, value) -> JsonPrimitiveValue.deserialize(value) }
            )
        }
    }
}
