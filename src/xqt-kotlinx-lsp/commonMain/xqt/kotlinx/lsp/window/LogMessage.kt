// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Parameters for `window/logMessage` notification.
 *
 * @since 1.0.0
 */
data class LogMessageParams(
    /**
     * The message type.
     */
    val type: MessageType,

    /**
     * The actual message.
     */
    val message: String
) {
    companion object : JsonSerialization<LogMessageParams> {
        override fun serializeToJson(value: LogMessageParams): JsonObject = buildJsonObject {
            put("type", value.type, MessageType)
            put("message", value.message, JsonString)
        }

        override fun deserialize(json: JsonElement): LogMessageParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> LogMessageParams(
                type = json.get("type", MessageType),
                message = json.get("message", JsonString)
            )
        }
    }
}
