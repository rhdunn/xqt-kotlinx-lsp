// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean

/**
 * The reference context.
 *
 * @since 1.0.0
 */
data class ReferenceContext(
    /**
     * Include the declaration of the current symbol.
     */
    val includeDeclaration: Boolean
) {
    companion object : JsonSerialization<ReferenceContext> {
        override fun serializeToJson(value: ReferenceContext): JsonObject = buildJsonObject {
            put("includeDeclaration", value.includeDeclaration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): ReferenceContext = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferenceContext(
                includeDeclaration = json.get("includeDeclaration", JsonBoolean)
            )
        }
    }
}
