// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Parameters for `workspace/symbol` request.
 *
 * @since 1.0.0
 */
data class WorkspaceSymbolParams(
    /**
     * A non-empty query string.
     */
    val query: String
) {
    companion object : JsonSerialization<WorkspaceSymbolParams> {
        override fun serializeToJson(value: WorkspaceSymbolParams): JsonObject = buildJsonObject {
            put("query", value.query, JsonString)
        }

        override fun deserialize(json: JsonElement): WorkspaceSymbolParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> WorkspaceSymbolParams(
                query = json.get("query", JsonString)
            )
        }
    }
}
