// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.telemetry

import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A notification in the `telemetry/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param notification the underlying notification message
 *
 * @since 2.0.0
 */
@JvmInline
value class TelemetryNotification(val notification: Notification) {
    companion object {
        /**
         * The telemetry notification is sent from the server to the client to ask the
         * client to log a telemetry event.
         */
        const val EVENT: String = "telemetry/event"
    }
}

/**
 * A notification in the `telemetry/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 2.0.0
 */
val Notification.telemetry: TelemetryNotification
    get() = TelemetryNotification(this)
