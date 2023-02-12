// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import kotlin.jvm.JvmInline

/**
 * Completion options.
 *
 * @since 1.0.0
 */
data class CompletionOptions(
    /**
     * The server provides support to resolve additional information for a
     * completion item.
     */
    val resolveProvider: Boolean? = null,

    /**
     * The characters that trigger completion automatically.
     */
    val triggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<CompletionOptions> {
        override fun serializeToJson(value: CompletionOptions): JsonObject = buildJsonObject {
            putOptional("resolveProvider", value.resolveProvider, JsonBoolean)
            putOptionalArray("triggerCharacters", value.triggerCharacters, JsonString)
        }

        override fun deserialize(json: JsonElement): CompletionOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CompletionOptions(
                resolveProvider = json.getOptional("resolveProvider", JsonBoolean),
                triggerCharacters = json.getOptionalArray("triggerCharacters", JsonString)
            )
        }
    }
}

/**
 * The completion entry kind.
 *
 * @param kind the completion entry kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class CompletionItemKind(val kind: Int) {
    companion object : JsonSerialization<CompletionItemKind> {
        override fun serializeToJson(value: CompletionItemKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): CompletionItemKind {
            return CompletionItemKind(JsonInt.deserialize(json))
        }

        /**
         * A text completion entry.
         */
        val Text: CompletionItemKind = CompletionItemKind(1)

        /**
         * A method completion entry.
         */
        val Method: CompletionItemKind = CompletionItemKind(2)

        /**
         * A function completion entry.
         */
        val Function: CompletionItemKind = CompletionItemKind(3)

        /**
         * A constructor completion entry.
         */
        val Constructor: CompletionItemKind = CompletionItemKind(4)

        /**
         * A field completion entry.
         */
        val Field: CompletionItemKind = CompletionItemKind(5)

        /**
         * A variable completion entry.
         */
        val Variable: CompletionItemKind = CompletionItemKind(6)

        /**
         * A class completion entry.
         */
        val Class: CompletionItemKind = CompletionItemKind(7)

        /**
         * An interface completion entry.
         */
        val Interface: CompletionItemKind = CompletionItemKind(8)

        /**
         * A module completion entry.
         */
        val Module: CompletionItemKind = CompletionItemKind(9)

        /**
         * A property completion entry.
         */
        val Property: CompletionItemKind = CompletionItemKind(10)

        /**
         * A unit completion entry.
         */
        val Unit: CompletionItemKind = CompletionItemKind(11)

        /**
         * A value completion entry.
         */
        val Value: CompletionItemKind = CompletionItemKind(12)

        /**
         * An enumeration completion entry.
         */
        val Enum: CompletionItemKind = CompletionItemKind(13)

        /**
         * A keyword completion entry.
         */
        val Keyword: CompletionItemKind = CompletionItemKind(14)

        /**
         * A snippet completion entry.
         */
        val Snippet: CompletionItemKind = CompletionItemKind(15)

        /**
         * A color completion entry.
         */
        val Color: CompletionItemKind = CompletionItemKind(16)

        /**
         * A file completion entry.
         */
        val File: CompletionItemKind = CompletionItemKind(17)

        /**
         * A reference completion entry.
         */
        val Reference: CompletionItemKind = CompletionItemKind(18)
    }
}
