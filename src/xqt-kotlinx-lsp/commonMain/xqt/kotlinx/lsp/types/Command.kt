// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPArray
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString

/**
 * Represents a reference to a command.
 *
 * Provides a title which will be used to represent a command in the UI. Commands
 * are identified using a string identifier and the protocol currently doesn't
 * specify a set of well known commands. So executing a command requires some tool
 * extension code.
 *
 * @since 1.0.0
 */
data class Command(
    /**
     * Title of the command, like `save`.
     */
    val title: String,

    /**
     * The identifier of the actual command handler.
     */
    val command: String,

    /**
     * Arguments that the command handler should be
     * invoked with.
     */
    val arguments: JsonArray? = null
) {
    companion object : JsonSerialization<Command> {
        override fun serializeToJson(value: Command): JsonObject = buildJsonObject {
            put("title", value.title, JsonString)
            put("command", value.command, JsonString)
            putOptional("arguments", value.arguments, LSPArray)
        }

        override fun deserialize(json: JsonElement): Command = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Command(
                title = json.get("title", JsonString),
                command = json.get("command", JsonString),
                arguments = json.getOptional("arguments", LSPArray)
            )
        }
    }
}
