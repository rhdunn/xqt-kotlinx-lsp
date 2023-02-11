// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.Integer
import xqt.kotlinx.lsp.base.LSPObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * The `initialize` request parameters.
 *
 * @since 1.0.0
 */
data class InitializeParams(
    /**
     * The process ID of the parent process that started the server.
     */
    val processId: Int,

    /**
     * The rootPath of the workspace.
     *
     * This is null if no folder is open.
     */
    val rootPath: String? = null,

    /**
     * The capabilities provided by the client (editor).
     */
    val capabilities: JsonObject
) {
    companion object : JsonSerialization<InitializeParams> {
        override fun serializeToJson(value: InitializeParams): JsonObject = buildJsonObject {
            put("processId", value.processId, Integer)
            putNullable("rootPath", value.rootPath, JsonString)
            put("capabilities", value.capabilities, LSPObject)
        }

        override fun deserialize(json: JsonElement): InitializeParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> InitializeParams(
                processId = json.get("processId", Integer),
                rootPath = json.getNullable("rootPath", JsonString),
                capabilities = json.get("capabilities", LSPObject)
            )
        }
    }
}
