// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*

/**
 * Parameters for `textDocument/documentLink` request.
 *
 * @since 2.0.0
 */
data class DocumentLinkParams(
    /**
     * The document to provide document links for.
     */
    val textDocument: TextDocumentIdentifier
) {
    companion object : JsonSerialization<DocumentLinkParams> {
        override fun serializeToJson(value: DocumentLinkParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
        }

        override fun deserialize(json: JsonElement): DocumentLinkParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentLinkParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier)
            )
        }
    }
}
