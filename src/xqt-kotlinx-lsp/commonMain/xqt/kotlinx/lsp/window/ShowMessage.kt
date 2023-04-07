// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType
import kotlin.jvm.JvmInline

/**
 * Parameters for `window/showMessage` notifications.
 *
 * @since 1.0.0
 */
data class ShowMessageParams(
    /**
     * The message type.
     */
    val type: MessageType,

    /**
     * The actual message.
     */
    val message: String
) {
    companion object : JsonSerialization<ShowMessageParams> {
        override fun serializeToJson(value: ShowMessageParams): JsonObject = buildJsonObject {
            put("type", value.type, MessageType)
            put("message", value.message, JsonString)
        }

        override fun deserialize(json: JsonElement): ShowMessageParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ShowMessageParams(
                type = json.get("type", MessageType),
                message = json.get("message", JsonString)
            )
        }
    }
}

/**
 * The message type.
 *
 * @param type the message type.
 *
 * @since 1.0.0
 */
@JvmInline
value class MessageType(val type: Int) {
    companion object : JsonSerialization<MessageType> {
        override fun serializeToJson(value: MessageType): JsonPrimitive = JsonPrimitive(value.type)

        override fun deserialize(json: JsonElement): MessageType {
            return MessageType(JsonInt.deserialize(json))
        }

        /**
         * An error message.
         */
        val Error: MessageType = MessageType(1)

        /**
         * A warning message.
         */
        val Warning: MessageType = MessageType(2)

        /**
         * An information message.
         */
        val Info: MessageType = MessageType(3)

        /**
         * A log message.
         */
        val Log: MessageType = MessageType(4)
    }
}

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @since 1.0.0
 */
fun WindowNotification.showMessage(handler: ShowMessageParams.() -> Unit) {
    if (notification.method == WindowNotification.SHOW_MESSAGE) {
        notification.params(ShowMessageParams).handler()
    }
}

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.showMessage(params: ShowMessageParams) = server.sendNotification(
    method = WindowNotification.SHOW_MESSAGE,
    params = ShowMessageParams.serializeToJson(params)
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @param type the message type
 * @param message the actual message
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.showMessage(type: MessageType, message: String) = showMessage(
    params = ShowMessageParams(type = type, message = message)
)
