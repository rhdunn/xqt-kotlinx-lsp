// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*

/**
 * The `textDocument/didSave` request parameters.
 *
 * @since 2.0.0
 */
data class DidSaveTextDocumentParams(
    /**
     * The document that was saved.
     */
    val textDocument: TextDocumentIdentifier
) {
    companion object : JsonSerialization<DidSaveTextDocumentParams> {
        override fun serializeToJson(value: DidSaveTextDocumentParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
        }

        override fun deserialize(json: JsonElement): DidSaveTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidSaveTextDocumentParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier)
            )
        }
    }
}
