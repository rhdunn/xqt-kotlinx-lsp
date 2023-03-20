// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendNotification
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

/**
 * Sent from the client to the server to signal the change of configuration settings.
 *
 * @param params the did change configuration parameters
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeConfiguration(params: DidChangeConfigurationParams) = server.sendNotification(
    method = DidChangeConfigurationParams.DID_CHANGE_CONFIGURATION,
    params = DidChangeConfigurationParams.serializeToJson(params)
)

/**
 * Sent from the client to the server to signal the change of configuration settings.
 *
 * @param settings the actual changed settings
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeConfiguration(settings: JsonElement) = didChangeConfiguration(
    params = DidChangeConfigurationParams(settings = settings)
)
