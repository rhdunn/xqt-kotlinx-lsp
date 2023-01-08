// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

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
