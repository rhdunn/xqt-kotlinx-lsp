// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean

/**
 * Capabilities specific to the `workspace/executeCommand` request.
 *
 * @since 3.0.0
 */
data class ExecuteCommandClientCapabilities(
    /**
     * Execute command supports dynamic registration.
     */
    val dynamicRegistration: Boolean? = null
) {
    companion object : JsonSerialization<ExecuteCommandClientCapabilities> {
        override fun serializeToJson(value: ExecuteCommandClientCapabilities): JsonObject = buildJsonObject {
            putOptional("dynamicRegistration", value.dynamicRegistration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): ExecuteCommandClientCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ExecuteCommandClientCapabilities(
                dynamicRegistration = json.getOptional("dynamicRegistration", JsonBoolean)
            )
        }
    }
}
