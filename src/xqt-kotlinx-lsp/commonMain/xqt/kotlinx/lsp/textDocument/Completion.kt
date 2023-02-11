// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonString

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
