// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * The `textDocument/rangeFormatting` request parameters.
 *
 * @since 1.0.0
 */
data class DocumentRangeFormattingParams(
    /**
     * The document to format.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The range to format.
     */
    val range: Range,

    /**
     * The format options.
     */
    val options: FormattingOptions
) {
    companion object : JsonSerialization<DocumentRangeFormattingParams> {
        override fun serializeToJson(value: DocumentRangeFormattingParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("range", value.range, Range)
            put("options", value.options, FormattingOptions)
        }

        override fun deserialize(json: JsonElement): DocumentRangeFormattingParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentRangeFormattingParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                range = json.get("range", Range),
                options = json.get("options", FormattingOptions)
            )
        }
    }
}

private val TextEditArray = JsonTypedArray(TextEdit)

/**
 * The document range formatting request is sent from the client to the server to format a
 * given range in a document.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.rangeFormatting(handler: DocumentRangeFormattingParams.() -> List<TextEdit>) {
    if (request.method == TextDocumentRequest.RANGE_FORMATTING) {
        val result = request.params(DocumentRangeFormattingParams).handler()
        request.sendResult(result, TextEditArray)
    }
}
