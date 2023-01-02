// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 *
 * @since 1.0.0
 */
data class WorkspaceEdit(
    /**
     * Holds changes to existing resources.
     */
    val changes: Map<String, List<TextEdit>>
) {
    companion object : JsonSerialization<WorkspaceEdit> {
        override fun serializeToJson(value: WorkspaceEdit): JsonObject = buildJsonObject {
            putMap("changes", value.changes, JsonString, TextEditArray)
        }

        override fun deserialize(json: JsonElement): WorkspaceEdit = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> WorkspaceEdit(
                changes = json.getMap("changes", JsonString, TextEditArray)
            )
        }
    }
}
