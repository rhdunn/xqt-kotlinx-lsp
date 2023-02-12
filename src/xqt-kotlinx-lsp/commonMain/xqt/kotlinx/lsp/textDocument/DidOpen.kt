// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Parameters for `textDocument/didOpen` notification.
 *
 * @since 1.0.0
 */
data class DidOpenTextDocumentParams(
    override val uri: String,

    /**
     * The content of the opened text document.
     */
    val text: String
) : TextDocumentIdentifier {
    companion object : JsonSerialization<DidOpenTextDocumentParams> {
        override fun serializeToJson(value: DidOpenTextDocumentParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("text", value.text, JsonString)
        }

        override fun deserialize(json: JsonElement): DidOpenTextDocumentParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidOpenTextDocumentParams(
                uri = json.get("uri", JsonString),
                text = json.get("text", JsonString)
            )
        }
    }
}
