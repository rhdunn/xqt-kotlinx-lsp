// Copyright (C) 2022-2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Identifies a position in a text document.
 *
 * __NOTE:__ This was defined as the `TextDocumentPosition` type in LSP 1.x.
 *
 * @since 2.0.0
 */
interface TextDocumentPositionParams : TextDocumentIdentifier {
    override val uri: String

    /**
     * The position inside the text document.
     */
    val position: Position

    companion object : JsonSerialization<TextDocumentPositionParams> {
        override fun serializeToJson(value: TextDocumentPositionParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("position", value.position, Position)
        }

        override fun deserialize(json: JsonElement): TextDocumentPositionParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextDocumentPositionParams(
                uri = json.get("uri", JsonString),
                position = json.get("position", Position)
            )
        }
    }
}

/**
 * Identifies a position in a text document.
 *
 * __NOTE:__ This was defined as the `TextDocumentPosition` type in LSP 1.x.
 *
 * @param uri the text document's URI
 * @param position the position inside the text document
 *
 * @since 2.0.0
 */
fun TextDocumentPositionParams(
    uri: String,
    position: Position
): TextDocumentPositionParams = object : TextDocumentPositionParams {
    override val uri: String = uri
    override val position: Position = position
}
