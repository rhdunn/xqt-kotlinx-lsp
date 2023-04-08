// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.telemetry

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.serialization.types.JsonArrayOrObject

/**
 * The telemetry notification is sent from the server to the client to ask the
 * client to log a telemetry event.
 *
 * @since 2.0.0
 */
fun TelemetryNotification.event(
    handler: JsonElement.() -> Unit
): Unit = notification.method(
    method = TelemetryNotification.EVENT,
    handler = handler,
    paramsSerializer = JsonArrayOrObject
)
