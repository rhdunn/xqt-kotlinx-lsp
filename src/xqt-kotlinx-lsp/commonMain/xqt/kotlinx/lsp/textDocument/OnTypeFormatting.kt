// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Format document on type options.
 *
 * @since 1.0.0
 */
data class DocumentOnTypeFormattingOptions(
    /**
     * A character on which formatting should be triggered, like `}`.
     */
    val firstTriggerCharacter: String,

    /**
     * More trigger characters.
     */
    val moreTriggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<DocumentOnTypeFormattingOptions> {
        private val JsonStringArray = JsonTypedArray(JsonString)

        override fun serializeToJson(value: DocumentOnTypeFormattingOptions): JsonObject = buildJsonObject {
            put("firstTriggerCharacter", value.firstTriggerCharacter, JsonString)
            // NOTE: The moreTriggerCharacters property is mistyped in the LSP specification.
            putOptional("moreTriggerCharacter", value.moreTriggerCharacters, JsonStringArray)
        }

        override fun deserialize(json: JsonElement): DocumentOnTypeFormattingOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentOnTypeFormattingOptions(
                firstTriggerCharacter = json.get("firstTriggerCharacter", JsonString),
                // NOTE: The moreTriggerCharacters property is mistyped in the LSP specification.
                moreTriggerCharacters = json.getOptional("moreTriggerCharacter", JsonStringArray)
            )
        }
    }
}
