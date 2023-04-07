// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.completionItem

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.completionItem.completionItem
import xqt.kotlinx.lsp.completionItem.resolve
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.lsp.textDocument.CompletionItemKind
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Completion Item DSL")
class CompletionItemDSL {
    // region completionItem/resolve request

    @Test
    @DisplayName("supports completionItem/resolve requests")
    fun supports_resolve_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("completionItem/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "label" to JsonPrimitive("Lorem Ipsum"),
                    "kind" to JsonPrimitive(1)
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                completionItem.resolve {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("completionItem/resolve", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("Lorem Ipsum", label)
                    assertEquals(CompletionItemKind.Text, kind)

                    this
                }
            }
        }

        assertEquals(true, called, "The completionItem.resolve DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "label" to JsonPrimitive("Lorem Ipsum"),
                    "kind" to JsonPrimitive(1)
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending completionItem/resolve requests using parameter objects")
    fun supports_sending_resolve_requests_using_parameter_objects() = testJsonRpc {
        val id = client.completionItem.resolve(
            params = CompletionItem(
                label = "Lorem Ipsum",
                kind = CompletionItemKind.Text
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("completionItem/resolve"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "label" to JsonPrimitive("Lorem Ipsum"),
                    "kind" to JsonPrimitive(1)
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports completionItem/resolve request callback receiving a result using parameter objects")
    fun supports_resolve_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.completionItem.resolve(
            params = CompletionItem(
                label = "Lorem Ipsum",
                kind = CompletionItemKind.Text
            )
        ) {
            ++called

            assertEquals(
                CompletionItem(
                    label = "Lorem Ipsum",
                    kind = CompletionItemKind.Text
                ),
                result
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                completionItem.resolve {
                    CompletionItem(
                        label = "Lorem Ipsum",
                        kind = CompletionItemKind.Text
                    )
                }
            }
        }

        assertEquals(0, called, "The completionItem.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "completionItem/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The completionItem.resolve DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports completionItem/resolve request callback receiving an error using parameter objects")
    fun supports_resolve_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.completionItem.resolve(
            params = CompletionItem(
                label = "Lorem Ipsum",
                kind = CompletionItemKind.Text
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                completionItem.resolve {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The completionItem.resolve DSL handler should not have been called.")
        client.jsonRpc {} // The "completionItem/resolve" response is processed by the handler callback.
        assertEquals(1, called, "The completionItem.resolve DSL handler should have been called.")
    }

    // endregion
}
