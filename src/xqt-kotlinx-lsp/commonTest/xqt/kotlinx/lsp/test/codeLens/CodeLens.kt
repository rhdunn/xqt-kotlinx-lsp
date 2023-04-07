// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.codeLens

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.codeLens.codeLens
import xqt.kotlinx.lsp.codeLens.resolve
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.CodeLens
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Code Lens DSL")
class TextCodeLensDSL {
    // region codeLens/resolve request

    @Test
    @DisplayName("supports codeLens/resolve requests")
    fun supports_resolve_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("codeLens/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                codeLens.resolve {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("codeLens/resolve", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    this
                }
            }
        }

        assertEquals(true, called, "The codeLens.resolve DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending codeLens/resolve requests using parameter objects")
    fun supports_sending_document_symbol_requests_using_parameter_objects() = testJsonRpc {
        val id = client.codeLens.resolve(
            params = CodeLens(
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("codeLens/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports codeLens/resolve request callback receiving a result using parameter objects")
    fun supports_document_symbol_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.codeLens.resolve(
            params = CodeLens(
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                )
            )
        ) {
            ++called

            assertEquals(Position(5u, 12u), result?.range?.start)
            assertEquals(Position(5u, 21u), result?.range?.end)

            assertEquals(null, result?.command)
            assertEquals(null, result?.data)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                codeLens.resolve {
                    this
                }
            }
        }

        assertEquals(0, called, "The codeLens.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "codeLens/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The codeLens.resolve DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports codeLens/resolve request callback receiving an error using parameter objects")
    fun supports_document_symbol_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.codeLens.resolve(
            params = CodeLens(
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                )
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                codeLens.resolve {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The codeLens.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "codeLens/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The codeLens.resolve DSL handler should have been called.")
    }

    // endregion
}
