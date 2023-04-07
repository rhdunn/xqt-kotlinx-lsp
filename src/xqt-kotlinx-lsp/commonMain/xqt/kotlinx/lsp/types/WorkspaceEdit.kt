// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedObject
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

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
        private val TextEditArrayMap = JsonTypedObject(
            keySerialization = JsonString,
            valueSerialization = JsonTypedArray(TextEdit)
        )

        override fun serializeToJson(value: WorkspaceEdit): JsonObject = buildJsonObject {
            put("changes", value.changes, TextEditArrayMap)
        }

        override fun deserialize(json: JsonElement): WorkspaceEdit = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> WorkspaceEdit(
                changes = json.get("changes", TextEditArrayMap)
            )
        }
    }
}
