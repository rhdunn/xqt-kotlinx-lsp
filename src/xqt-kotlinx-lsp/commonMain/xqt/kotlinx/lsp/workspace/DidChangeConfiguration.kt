// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.lsp.window.ShowMessageParams
import xqt.kotlinx.lsp.window.WindowNotification
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.serialization.*

/**
 * Parameters for `workspace/didChangeConfiguration` notification.
 *
 * @since 1.0.0
 */
data class DidChangeConfigurationParams(
    /**
     * The actual changed settings.
     */
    val settings: JsonElement
) {
    companion object : JsonSerialization<DidChangeConfigurationParams> {
        internal const val DID_CHANGE_CONFIGURATION: String = "workspace/didChangeConfiguration"

        override fun serializeToJson(value: DidChangeConfigurationParams): JsonObject = buildJsonObject {
            put("settings", value.settings, LSPAny)
        }

        override fun deserialize(json: JsonElement): DidChangeConfigurationParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidChangeConfigurationParams(
                settings = json.get("settings", LSPAny)
            )
        }
    }
}

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @since 1.0.0
 */
fun WorkspaceNotification.didChangeConfiguration(handler: DidChangeConfigurationParams.() -> Unit) {
    if (notification.method == DidChangeConfigurationParams.DID_CHANGE_CONFIGURATION) {
        notification.params(DidChangeConfigurationParams).handler()
    }
}
