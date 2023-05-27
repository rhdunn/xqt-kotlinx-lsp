// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.Integer
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * An identifier to denote a specific version of a text document.
 *
 * @since 2.0.0
 */
data class VersionedTextDocumentIdentifier(
    override val uri: DocumentUri,

    /**
     * The version number of this document.
     */
    val version: Int
) : TextDocumentIdentifier {
    companion object : JsonSerialization<VersionedTextDocumentIdentifier> {
        override fun serializeToJson(value: VersionedTextDocumentIdentifier): JsonObject = buildJsonObject {
            put("uri", value.uri, DocumentUri)
            put("version", value.version, Integer)
        }

        override fun deserialize(json: JsonElement): VersionedTextDocumentIdentifier = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> VersionedTextDocumentIdentifier(
                uri = json.get("uri", DocumentUri),
                version = json.get("version", Integer)
            )
        }
    }
}
