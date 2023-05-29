// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.documentLink

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.documentLink.documentLink
import xqt.kotlinx.lsp.documentLink.resolve
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.DocumentLink
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Document Link DSL")
class DocumentLinkDSL {
    // region documentLink/resolve request

    @Test
    @DisplayName("supports documentLink/resolve requests")
    fun supports_resolve_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("documentLink/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(8)
                        )
                    ),
                    "target" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                documentLink.resolve {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("documentLink/resolve", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals(Position(2u, 6u), range.start)
                    assertEquals(Position(2u, 8u), range.end)
                    assertEquals("file:///home/lorem/ipsum.py", target)

                    this
                }
            }
        }

        assertEquals(true, called, "The documentLink.resolve DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(8)
                        )
                    ),
                    "target" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending documentLink/resolve requests using parameter objects")
    fun supports_sending_resolve_requests_using_parameter_objects() = testJsonRpc {
        val id = client.documentLink.resolve(
            params = DocumentLink(
                range = Range(
                    start = Position(line = 2u, character = 6u),
                    end = Position(line = 2u, character = 8u)
                ),
                target = "file:///home/lorem/dolor.py"
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("documentLink/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(8)
                        )
                    ),
                    "target" to JsonPrimitive("file:///home/lorem/dolor.py")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports documentLink/resolve request callback receiving a result using parameter objects")
    fun supports_resolve_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.documentLink.resolve(
            params = DocumentLink(
                range = Range(
                    start = Position(line = 2u, character = 6u),
                    end = Position(line = 2u, character = 8u)
                ),
                target = "file:///home/lorem/dolor.py"
            )
        ) {
            ++called

            assertEquals(
                DocumentLink(
                    range = Range(
                        start = Position(line = 2u, character = 6u),
                        end = Position(line = 2u, character = 8u)
                    ),
                    target = "file:///home/lorem/dolor.py"
                ),
                result
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                documentLink.resolve {
                    this
                }
            }
        }

        assertEquals(0, called, "The documentLink.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "documentLink/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The documentLink.resolve DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports completionItem/resolve request callback receiving an error using parameter objects")
    fun supports_resolve_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.documentLink.resolve(
            params = DocumentLink(
                range = Range(
                    start = Position(line = 2u, character = 6u),
                    end = Position(line = 2u, character = 8u)
                ),
                target = "file:///home/lorem/dolor.py"
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                documentLink.resolve {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The documentLink.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "documentLink/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The documentLink.resolve DSL handler should have been called.")
    }

    // endregion
}
