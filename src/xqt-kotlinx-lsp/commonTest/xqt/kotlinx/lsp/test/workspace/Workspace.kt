// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.workspace

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.workspace.DidChangeConfigurationParams
import xqt.kotlinx.lsp.workspace.didChangeConfiguration
import xqt.kotlinx.lsp.workspace.workspace
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Workspace DSL")
class WorkspaceDSL {
    @Test
    @DisplayName("supports workspace/didChangeConfiguration notifications")
    fun supports_did_change_configuration_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeConfiguration"),
                "params" to jsonObjectOf(
                    "settings" to JsonPrimitive("test")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                workspace.didChangeConfiguration {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("workspace/didChangeConfiguration", method)

                    assertEquals(JsonPrimitive("test"), settings)
                }
            }
        }

        assertEquals(true, called, "The workspace.didChangeConfiguration DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending workspace/didChangeConfiguration notifications using DidChangeConfigurationParams")
    fun supports_sending_did_change_configuration_notifications_using_class_params() = testJsonRpc {
        server.workspace.didChangeConfiguration(
            params = DidChangeConfigurationParams(
                settings = JsonPrimitive("Lorem Ipsum")
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeConfiguration"),
                "params" to jsonObjectOf(
                    "settings" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending window/logMessage notifications using function parameters")
    fun supports_sending_log_message_notifications_using_function_parameters() = testJsonRpc {
        server.workspace.didChangeConfiguration(
            settings = JsonPrimitive("Lorem Ipsum")
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeConfiguration"),
                "params" to jsonObjectOf(
                    "settings" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }
}
