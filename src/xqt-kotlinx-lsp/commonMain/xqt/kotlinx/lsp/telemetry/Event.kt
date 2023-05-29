// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.telemetry

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.types.JsonArrayOrObject

/**
 * The telemetry notification is sent from the server to the client to ask the
 * client to log a telemetry event.
 *
 * @since 2.0.0 (LSP 2.1.0)
 */
fun TelemetryNotification.event(
    handler: JsonElement.() -> Unit
): Unit = notification.method(
    method = TelemetryNotification.EVENT,
    handler = handler,
    paramsSerializer = JsonArrayOrObject
)

/**
 * The telemetry notification is sent from the server to the client to ask the
 * client to log a telemetry event.
 *
 * @param params the notification parameters
 *
 * @since 2.0.0 (LSP 2.1.0)
 */
fun TelemetryJsonRpcServer.event(
    params: JsonElement
): Unit = server.sendNotification(
    method = TelemetryNotification.EVENT,
    params = LSPAny.serializeToJson(params)
)
