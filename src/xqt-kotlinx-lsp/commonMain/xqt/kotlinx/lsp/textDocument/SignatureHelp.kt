// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Signature help options.
 *
 * @since 1.0.0
 */
data class SignatureHelpOptions(
    /**
     * The characters that trigger signature help automatically.
     */
    val triggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<SignatureHelpOptions> {
        override fun serializeToJson(value: SignatureHelpOptions): JsonObject = buildJsonObject {
            putOptionalArray("triggerCharacters", value.triggerCharacters, JsonString)
        }

        override fun deserialize(json: JsonElement): SignatureHelpOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> SignatureHelpOptions(
                triggerCharacters = json.getOptionalArray("triggerCharacters", JsonString)
            )
        }
    }
}
