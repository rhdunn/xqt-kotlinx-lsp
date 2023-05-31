// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.uri.UriScheme

/**
 * A document filter denotes a document through properties like `language`,
 * `schema` or `pattern`.
 *
 * @since 3.0.0
 */
data class DocumentFilter(
    /**
     * A language id, like `typescript`.
     */
    val language: String? = null,

    /**
     * A Uri scheme, like `file` or `untitled`.
     */
    val scheme: UriScheme? = null,

    /**
     * A glob pattern, like `*.{ts,js}`.
     */
    val pattern: String? = null
) {
    companion object : JsonSerialization<DocumentFilter> {
        override fun serializeToJson(value: DocumentFilter): JsonObject = buildJsonObject {
            putOptional("language", value.language, JsonString)
            putOptional("scheme", value.scheme, UriScheme)
            putOptional("pattern", value.pattern, JsonString)
        }

        override fun deserialize(json: JsonElement): DocumentFilter = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentFilter(
                language = json.getOptional("language", JsonString),
                scheme = json.getOptional("scheme", UriScheme),
                pattern = json.getOptional("pattern", JsonString)
            )
        }
    }
}
