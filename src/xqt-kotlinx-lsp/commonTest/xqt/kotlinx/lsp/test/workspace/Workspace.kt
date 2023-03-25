// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.workspace

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.workspace.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Workspace DSL")
class WorkspaceDSL {
    // region workspace/didChangeConfiguration notification

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
    @DisplayName("supports sending workspace/didChangeConfiguration notifications using function parameters")
    fun supports_sending_did_change_configuration_notifications_using_function_parameters() = testJsonRpc {
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

    // endregion
    // region textDocument/didChangeWatchedFiles notification

    @Test
    @DisplayName("supports workspace/didChangeWatchedFiles notifications")
    fun supports_did_change_watched_files_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeWatchedFiles"),
                "params" to jsonObjectOf(
                    "changes" to jsonArrayOf()
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                workspace.didChangeWatchedFiles {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("workspace/didChangeWatchedFiles", method)

                    assertEquals(0, changes.size)
                }
            }
        }

        assertEquals(true, called, "The workspace.didChangeWatchedFiles DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending workspace/didChangeWatchedFiles notifications using DidChangeWatchedFilesParams")
    fun supports_sending_did_change_watched_files_notifications_using_class_params() = testJsonRpc {
        server.workspace.didChangeWatchedFiles(
            params = DidChangeWatchedFilesParams(
                changes = listOf()
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeWatchedFiles"),
                "params" to jsonObjectOf(
                    "changes" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending workspace/didChangeWatchedFiles notifications using function parameters")
    fun supports_sending_did_change_watched_files_notifications_using_function_parameters() = testJsonRpc {
        server.workspace.didChangeWatchedFiles(
            changes = listOf()
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/didChangeWatchedFiles"),
                "params" to jsonObjectOf(
                    "changes" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    // endregion
}
