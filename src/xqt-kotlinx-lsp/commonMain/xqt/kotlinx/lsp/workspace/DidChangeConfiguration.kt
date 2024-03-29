// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean

/**
 * Capabilities specific to the `workspace/didChangeConfiguration` notification.
 *
 * @since 3.0.0
 */
data class DidChangeConfigurationClientCapabilities(
    /**
     * Did change configuration notification supports dynamic registration.
     */
    val dynamicRegistration: Boolean? = null
) {
    companion object : JsonSerialization<DidChangeConfigurationClientCapabilities> {
        override fun serializeToJson(value: DidChangeConfigurationClientCapabilities): JsonObject = buildJsonObject {
            putOptional("dynamicRegistration", value.dynamicRegistration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): DidChangeConfigurationClientCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidChangeConfigurationClientCapabilities(
                dynamicRegistration = json.getOptional("dynamicRegistration", JsonBoolean)
            )
        }
    }
}

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
fun WorkspaceNotification.didChangeConfiguration(
    handler: DidChangeConfigurationParams.() -> Unit
): Unit = notification.method(
    method = WorkspaceNotification.DID_CHANGE_CONFIGURATION,
    handler = handler,
    paramsSerializer = DidChangeConfigurationParams
)

/**
 * Sent from the client to the server to signal the change of configuration settings.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeConfiguration(
    params: DidChangeConfigurationParams
): Unit = server.sendNotification(
    method = WorkspaceNotification.DID_CHANGE_CONFIGURATION,
    params = DidChangeConfigurationParams.serializeToJson(params)
)

/**
 * Sent from the client to the server to signal the change of configuration settings.
 *
 * @param settings the actual changed settings
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeConfiguration(
    settings: JsonElement
): Unit = didChangeConfiguration(
    params = DidChangeConfigurationParams(settings = settings)
)
