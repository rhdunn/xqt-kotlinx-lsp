// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonPrimitiveValue
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

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
    val insertSpaces: Boolean,

    /**
     * Signature for further options.
     *
     * In this implementation all options are included in the map.
     */
    private val options: Map<String, JsonPrimitive>
) : Map<String, JsonPrimitive> by options {
    constructor(
        tabSize: UInt,
        insertSpaces: Boolean
    ) : this(
        tabSize = tabSize,
        insertSpaces = insertSpaces,
        options = mapOf(
            "tabSize" to UInteger.serializeToJson(tabSize),
            "insertSpaces" to JsonBoolean.serializeToJson(insertSpaces)
        )
    )

    companion object : JsonSerialization<FormattingOptions> {
        override fun serializeToJson(value: FormattingOptions): JsonObject = JsonObject(value.options)

        override fun deserialize(json: JsonElement): FormattingOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> FormattingOptions(
                tabSize = json.get("tabSize", UInteger),
                insertSpaces = json.get("insertSpaces", JsonBoolean),
                options = json.mapValues { (_, value) -> JsonPrimitiveValue.deserialize(value) }
            )
        }
    }
}

private val TextEditArray = JsonTypedArray(TextEdit)

/**
 * The document formatting request is sent from the server to the client to format a whole
 * document.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.formatting(handler: DocumentFormattingParams.() -> List<TextEdit>) {
    if (request.method == TextDocumentRequest.FORMATTING) {
        val result = request.params(DocumentFormattingParams).handler()
        request.sendResult(result, TextEditArray)
    }
}
