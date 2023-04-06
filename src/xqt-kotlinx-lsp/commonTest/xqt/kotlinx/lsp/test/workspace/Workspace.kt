// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.workspace

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.workspace.workspace
import xqt.kotlinx.lsp.workspace.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
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
    // region workspace/didChangeWatchedFiles notification

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
    // region workspace/symbol request

    @Test
    @DisplayName("supports workspace/symbol requests")
    fun supports_symbol_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/symbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "query" to JsonPrimitive("lorem")
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                workspace.symbol {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("workspace/symbol", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("lorem", query)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The workspace.symbol DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonArrayOf()
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending workspace/symbol requests using parameter objects")
    fun supports_sending_symbol_requests_using_parameter_objects() = testJsonRpc {
        val id = client.workspace.symbol(
            params = WorkspaceSymbolParams(
                query = "lorem"
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/symbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "query" to JsonPrimitive("lorem")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports workspace/symbol request callback receiving a result using parameter objects")
    fun supports_symbol_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.workspace.symbol(
            params = WorkspaceSymbolParams(
                query = "lorem"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                workspace.symbol {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The workspace.symbol DSL handler should not have been called.")
        client.jsonRpc {} // The "workspace/symbol" response is processed by the handler callback.
        assertEquals(1, called, "The workspace.symbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports workspace/symbol request callback receiving an error using parameter objects")
    fun supports_document_symbol_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.workspace.symbol(
            params = WorkspaceSymbolParams(
                query = "lorem"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                workspace.symbol {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The workspace.symbol DSL handler should not have been called.")
        client.jsonRpc {} // The "workspace/symbol" response is processed by the handler callback.
        assertEquals(1, called, "The workspace.symbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending workspace/symbol requests using function parameters")
    fun supports_sending_document_symbol_requests_using_function_parameters() = testJsonRpc {
        val id = client.workspace.symbol(
            query = "lorem"
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("workspace/symbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "query" to JsonPrimitive("lorem")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports workspace/symbol request callback receiving a result using function parameters")
    fun supports_document_symbol_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.workspace.symbol(
            query = "lorem"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                workspace.symbol {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The workspace.symbol DSL handler should not have been called.")
        client.jsonRpc {} // The "workspace/symbol" response is processed by the handler callback.
        assertEquals(1, called, "The workspace.symbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports workspace/symbol request callback receiving an error using function parameters")
    fun supports_document_symbol_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.workspace.symbol(
            query = "lorem"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                workspace.symbol {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The workspace.symbol DSL handler should not have been called.")
        client.jsonRpc {} // The "workspace/symbol" response is processed by the handler callback.
        assertEquals(1, called, "The workspace.symbol DSL handler should have been called.")
    }

    // endregion
}
