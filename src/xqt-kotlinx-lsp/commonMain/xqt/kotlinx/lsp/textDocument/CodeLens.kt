// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean

/**
 * Code lens options.
 *
 * @since 1.0.0
 */
data class CodeLensOptions(
    /**
     * The server provides support to resolve additional information for a
     * code lens.
     */
    val resolveProvider: Boolean? = null
) {
    companion object : JsonSerialization<CodeLensOptions> {
        override fun serializeToJson(value: CodeLensOptions): JsonObject = buildJsonObject {
            putOptional("resolveProvider", value.resolveProvider, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): CodeLensOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CodeLensOptions(
                resolveProvider = json.getOptional("resolveProvider", JsonBoolean)
            )
        }
    }
}
