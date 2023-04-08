// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.telemetry

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.telemetry.event
import xqt.kotlinx.lsp.telemetry.telemetry
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Telemetry DSL")
class TelemetryDSL {
    // region telemetry/event notification

    @Test
    @DisplayName("supports telemetry/event notifications")
    fun supports_event_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("telemetry/event"),
                "params" to jsonObjectOf(
                    "value" to JsonPrimitive("test")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                telemetry.event {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("telemetry/event", method)

                    assertEquals(jsonObjectOf("value" to JsonPrimitive("test")), this)
                }
            }
        }

        assertEquals(true, called, "The telemetry.event DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending telemetry/event notifications using parameter objects")
    fun supports_sending_event_notifications_using_parameter_objects() = testJsonRpc {
        server.telemetry.event(
            params = jsonObjectOf(
                "value" to JsonPrimitive("test")
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("telemetry/event"),
                "params" to jsonObjectOf(
                    "value" to JsonPrimitive("test")
                )
            ),
            client.receive()
        )
    }

    // endregion
}
