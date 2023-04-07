// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

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

/**
 * Ask the client to log a particular message.
 *
 * @since 1.0.0
 */
fun WindowNotification.logMessage(handler: LogMessageParams.() -> Unit) {
    if (notification.method == WindowNotification.LOG_MESSAGE) {
        notification.params(LogMessageParams).handler()
    }
}

/**
 * Ask the client to log a particular message.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.logMessage(params: LogMessageParams) = server.sendNotification(
    method = WindowNotification.LOG_MESSAGE,
    params = LogMessageParams.serializeToJson(params)
)

/**
 * Ask the client to log a particular message.
 *
 * @param type the message type
 * @param message the actual message
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.logMessage(type: MessageType, message: String) = logMessage(
    params = LogMessageParams(type = type, message = message)
)
