// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonStringEnumerationType
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.uri.UriScheme
import kotlin.jvm.JvmInline

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
    val language: Language? = null,

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
            putOptional("language", value.language, Language)
            putOptional("scheme", value.scheme, UriScheme)
            putOptional("pattern", value.pattern, JsonString)
        }

        override fun deserialize(json: JsonElement): DocumentFilter = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentFilter(
                language = json.getOptional("language", Language),
                scheme = json.getOptional("scheme", UriScheme),
                pattern = json.getOptional("pattern", JsonString)
            )
        }
    }
}

/**
 * A document selector is the combination of one or many document filters.
 *
 * @since 3.0.0
 */
val DocumentSelector = JsonTypedArray(DocumentFilter)

/**
 * A programming language.
 *
 * NOTE: This is an extension to the LSP protocol. LSP passes this around as a
 * string.
 *
 * @param kind the identifier for the programming language
 *
 * @since 3.0.0
 */
@JvmInline
value class Language(override val kind: String) : JsonEnumeration<String> {
    override fun toString(): String = kind

    companion object : JsonStringEnumerationType<Language>() {
        override fun valueOf(value: String): Language = Language(value)
    }
}
