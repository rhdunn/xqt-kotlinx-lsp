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
 * Represents a location inside a resource, such as a line inside a text file.
 *
 * @since 1.0.0
 */
data class Location(
    /**
     * The text document's URI.
     */
    val uri: DocumentUri,

    /**
     * The location inside the document.
     */
    val range: Range
) {
    companion object : JsonSerialization<Location> {
        override fun serializeToJson(value: Location): JsonObject = buildJsonObject {
            put("uri", value.uri, DocumentUri)
            put("range", value.range, Range)
        }

        override fun deserialize(json: JsonElement): Location = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Location(
                uri = json.get("uri", DocumentUri),
                range = json.get("range", Range)
            )
        }
    }
}
