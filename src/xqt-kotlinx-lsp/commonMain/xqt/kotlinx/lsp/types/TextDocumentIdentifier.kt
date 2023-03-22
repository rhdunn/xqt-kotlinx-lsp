// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
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
 * Text documents are identified using a URI.
 *
 * On the protocol level, URIs are passed as strings.
 *
 * @since 1.0.0
 */
interface TextDocumentIdentifier {
    /**
     * The text document's URI.
     */
    val uri: String

    companion object : JsonSerialization<TextDocumentIdentifier> {
        override fun serializeToJson(value: TextDocumentIdentifier): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
        }

        override fun deserialize(json: JsonElement): TextDocumentIdentifier = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> TextDocumentIdentifier(
                uri = json.get("uri", JsonString)
            )
        }
    }
}

/**
 * Text documents are identified using a URI.
 *
 * @param uri the text document's URI
 *
 * @since 1.0.0
 */
fun TextDocumentIdentifier(uri: String): TextDocumentIdentifier = object : TextDocumentIdentifier {
    override val uri: String = uri
}
