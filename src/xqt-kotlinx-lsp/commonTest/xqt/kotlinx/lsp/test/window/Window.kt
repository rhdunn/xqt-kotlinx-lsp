// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.window

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.window.MessageType
import xqt.kotlinx.lsp.window.ShowMessageParams
import xqt.kotlinx.lsp.window.showMessage
import xqt.kotlinx.lsp.window.window
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Window DSL")
class WindowDSL {
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
}
