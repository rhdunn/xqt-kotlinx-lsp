// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.lifecycle.*
import xqt.kotlinx.lsp.test.base.TestJsonRpcChannel
import xqt.kotlinx.lsp.textDocument.TextDocumentSyncKind
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Lifecycle DSL")
class LifecycleDSL {
    @Test
    @DisplayName("supports initialize requests returning an InitializeResult")
    fun supports_initialize_requests_returning_an_initialize_result() {
        val channel = TestJsonRpcChannel()
        channel.input.add(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            )
        )

        var called = false
        channel.jsonRpc {
            request {
                initialize {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("initialize", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals(1234, processId)
                    assertEquals(null, rootPath)
                    assertEquals(jsonObjectOf("test" to JsonPrimitive("lorem ipsum")), capabilities)

                    InitializeResult(
                        capabilities = ServerCapabilities(
                            textDocumentSync = TextDocumentSyncKind.Full
                        )
                    )
                }
            }
        }

        assertEquals(true, called, "The initialize DSL should have been called.")
        assertEquals(1, channel.output.size)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "capabilities" to jsonObjectOf(
                        "textDocumentSync" to JsonPrimitive(1)
                    )
                )
            ),
            channel.output[0]
        )
    }

    @Test
    @DisplayName("supports initialize requests throwing an InitializeError")
    fun supports_initialize_requests_throwing_an_initialize_error() {
        val channel = TestJsonRpcChannel()
        channel.input.add(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            )
        )

        var called = false
        channel.jsonRpc {
            request {
                initialize {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("initialize", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals(1234, processId)
                    assertEquals(null, rootPath)
                    assertEquals(jsonObjectOf("test" to JsonPrimitive("lorem ipsum")), capabilities)

                    throw InitializeError(message = "Lorem ipsum", retry = true)
                }
            }
        }

        assertEquals(true, called, "The initialize DSL should have been called.")
        assertEquals(1, channel.output.size)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "error" to jsonObjectOf(
                    "code" to JsonPrimitive(ErrorCodes.InternalError.code),
                    "message" to JsonPrimitive("Lorem ipsum"),
                    "data" to jsonObjectOf(
                        "retry" to JsonPrimitive(true)
                    )
                )
            ),
            channel.output[0]
        )
    }

    @Test
    @DisplayName("supports shutdown requests")
    fun supports_shutdown_requests() {
        val channel = TestJsonRpcChannel()
        channel.input.add(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("shutdown"),
                "id" to JsonPrimitive(1)
            )
        )

        var called = false
        channel.jsonRpc {
            request {
                shutdown {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("shutdown", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)
                }
            }
        }

        assertEquals(true, called, "The shutdown DSL should have been called.")
        assertEquals(1, channel.output.size)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to JsonNull
            ),
            channel.output[0]
        )
    }

    @Test
    @DisplayName("supports exit notifications")
    fun supports_exit_notifications() {
        val channel = TestJsonRpcChannel()
        channel.input.add(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("exit")
            )
        )

        var called = false
        channel.jsonRpc {
            notification {
                exit {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("exit", method)
                }
            }
        }

        assertEquals(true, called, "The exit DSL should have been called.")
        assertEquals(0, channel.output.size)
    }
}
