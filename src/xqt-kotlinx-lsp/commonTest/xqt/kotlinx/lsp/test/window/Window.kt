// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.window

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.window.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Window DSL")
class WindowDSL {
    // region window/showMessage notification

    @Test
    @DisplayName("supports window/showMessage notifications")
    fun supports_show_message_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/showMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                window.showMessage {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("window/showMessage", method)

                    assertEquals(MessageType.Warning, type)
                    assertEquals("Lorem Ipsum", message)
                }
            }
        }

        assertEquals(true, called, "The window.showMessage DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending window/showMessage notifications using ShowMessageParams")
    fun supports_sending_show_message_notifications_using_show_message_params() = testJsonRpc {
        server.window.showMessage(
            params = ShowMessageParams(
                type = MessageType.Warning,
                message = "Lorem Ipsum"
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/showMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending window/showMessage notifications using function parameters")
    fun supports_sending_show_message_notifications_using_function_parameters() = testJsonRpc {
        server.window.showMessage(
            type = MessageType.Warning,
            message = "Lorem Ipsum"
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/showMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    // endregion
    // region window/logMessage notification

    @Test
    @DisplayName("supports window/logMessage notifications")
    fun supports_log_message_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/logMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                window.logMessage {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("window/logMessage", method)

                    assertEquals(MessageType.Warning, type)
                    assertEquals("Lorem Ipsum", message)
                }
            }
        }

        assertEquals(true, called, "The window.logMessage DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending window/logMessage notifications using LogMessageParams")
    fun supports_sending_log_message_notifications_using_log_message_params() = testJsonRpc {
        server.window.logMessage(
            params = LogMessageParams(
                type = MessageType.Warning,
                message = "Lorem Ipsum"
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/logMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending window/logMessage notifications using function parameters")
    fun supports_sending_log_message_notifications_using_function_parameters() = testJsonRpc {
        server.window.logMessage(
            type = MessageType.Warning,
            message = "Lorem Ipsum"
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("window/logMessage"),
                "params" to jsonObjectOf(
                    "type" to JsonPrimitive(2),
                    "message" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    // endregion
}
