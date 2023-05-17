// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.lsp.types.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Text Document DSL")
class TextDocumentDSL {
    // region textDocument/didOpen notification

    @Test
    @DisplayName("supports textDocument/didOpen notifications")
    fun supports_did_open_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didOpen"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "text" to JsonPrimitive("Lorem Ipsum")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                textDocument.didOpen {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/didOpen", method)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals("Lorem Ipsum", text)
                }
            }
        }

        assertEquals(true, called, "The textDocument.didOpen DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/didOpen notifications using parameter objects")
    fun supports_sending_did_open_notifications_using_parameter_objects() = testJsonRpc {
        server.textDocument.didOpen(
            params = DidOpenTextDocumentParams(
                uri = "file:///home/lorem/ipsum.py",
                text = "Lorem Ipsum"
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didOpen"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "text" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/didOpen notifications using function parameters")
    fun supports_sending_did_open_notifications_using_function_parameters() = testJsonRpc {
        server.textDocument.didOpen(
            uri = "file:///home/lorem/ipsum.py",
            text = "Lorem Ipsum"
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didOpen"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "text" to JsonPrimitive("Lorem Ipsum")
                )
            ),
            client.receive()
        )
    }

    // endregion
    // region textDocument/didChange notification

    @Test
    @DisplayName("supports textDocument/didChange notifications")
    fun supports_did_change_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didChange"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "contentChanges" to jsonArrayOf()
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                textDocument.didChange {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/didChange", method)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(0, contentChanges.size)
                }
            }
        }

        assertEquals(true, called, "The textDocument.didChange DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/didChange notifications using parameter objects")
    fun supports_sending_did_change_notifications_using_parameter_objects() = testJsonRpc {
        server.textDocument.didChange(
            params = DidChangeTextDocumentParams(
                uri = "file:///home/lorem/ipsum.py",
                contentChanges = listOf()
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didChange"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "contentChanges" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/didChange notifications using function parameters")
    fun supports_sending_did_change_notifications_using_function_parameters() = testJsonRpc {
        server.textDocument.didChange(
            uri = "file:///home/lorem/ipsum.py",
            contentChanges = listOf()
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didChange"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "contentChanges" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    // endregion
    // region textDocument/didClose notification

    @Test
    @DisplayName("supports textDocument/didClose notifications")
    fun supports_did_close_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didClose"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                textDocument.didClose {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/didClose", method)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                }
            }
        }

        assertEquals(true, called, "The textDocument.didClose DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/didClose notifications using parameter objects")
    fun supports_sending_did_close_notifications_using_parameter_objects() = testJsonRpc {
        server.textDocument.didClose(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didClose"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/didClose notifications using function parameters")
    fun supports_sending_did_close_notifications_using_function_parameters() = testJsonRpc {
        server.textDocument.didClose(
            uri = "file:///home/lorem/ipsum.py"
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/didClose"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            client.receive()
        )
    }

    // endregion
    // region textDocument/publishDiagnostics notification

    @Test
    @DisplayName("supports textDocument/publishDiagnostics notifications")
    fun supports_publish_diagnostics_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/publishDiagnostics"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "diagnostics" to jsonArrayOf()
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                textDocument.publishDiagnostics {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/publishDiagnostics", method)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(0, diagnostics.size)
                }
            }
        }

        assertEquals(true, called, "The textDocument.publishDiagnostics DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/publishDiagnostics notifications using parameter objects")
    fun supports_sending_publish_diagnostics_notifications_using_parameter_objects() = testJsonRpc {
        server.textDocument.publishDiagnostics(
            params = PublishDiagnosticsParams(
                uri = "file:///home/lorem/ipsum.py",
                diagnostics = listOf()
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/publishDiagnostics"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "diagnostics" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/publishDiagnostics notifications using function parameters")
    fun supports_sending_publish_diagnostics_notifications_using_function_parameters() = testJsonRpc {
        server.textDocument.publishDiagnostics(
            uri = "file:///home/lorem/ipsum.py",
            diagnostics = listOf()
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/publishDiagnostics"),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "diagnostics" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    // endregion
    // region textDocument/completion request

    @Test
    @DisplayName("supports textDocument/completion requests")
    fun supports_completion_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/completion"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.completion {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/completion", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    listOf(
                        CompletionItem(
                            label = "Lorem Ipsum",
                            kind = CompletionItemKind.Text
                        )
                    )
                }
            }
        }

        assertEquals(true, called, "The textDocument.completion DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonArrayOf(
                    jsonObjectOf(
                        "label" to JsonPrimitive("Lorem Ipsum"),
                        "kind" to JsonPrimitive(1)
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/completion requests using parameter objects")
    fun supports_sending_completion_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.completion(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/completion"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/completion request callback receiving a result using parameter objects")
    fun supports_completion_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(1, result.size)
            assertEquals(
                CompletionItem(
                    label = "Lorem Ipsum",
                    kind = CompletionItemKind.Text
                ),
                result[0]
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.completion {
                    listOf(
                        CompletionItem(
                            label = "Lorem Ipsum",
                            kind = CompletionItemKind.Text
                        )
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.completion DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/completion" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.completion DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/completion request callback receiving an error using parameter objects")
    fun supports_completion_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.completion {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.completion DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/completion" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.completion DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/completion requests using function parameters")
    fun supports_sending_completion_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.completion(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/completion"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/completion request callback receiving a result using function parameters")
    fun supports_completion_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(1, result.size)
            assertEquals(
                CompletionItem(
                    label = "Lorem Ipsum",
                    kind = CompletionItemKind.Text
                ),
                result[0]
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.completion {
                    listOf(
                        CompletionItem(
                            label = "Lorem Ipsum",
                            kind = CompletionItemKind.Text
                        )
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.completion DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/completion" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.completion DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/completion request callback receiving an error using function parameters")
    fun supports_completion_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.completion {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.completion DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/completion" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.completion DSL handler should have been called.")
    }

    // endregion
    // region textDocument/hover request

    @Test
    @DisplayName("supports textDocument/hover requests")
    fun supports_hover_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/hover"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.hover {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/hover", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    Hover(
                        contents = listOf(),
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(true, called, "The textDocument.hover DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "contents" to jsonArrayOf(),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        )
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/hover requests using parameter objects")
    fun supports_sending_hover_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.hover(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/hover"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/hover request callback receiving a result using parameter objects")
    fun supports_hover_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(0, result?.contents?.size)
            assertEquals(
                Range(Position(2u, 6u), Position(2u, 6u)),
                result?.range
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.hover {
                    Hover(
                        contents = listOf(),
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.hover DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/hover" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.hover DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/hover request callback receiving an error using parameter objects")
    fun supports_hover_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.hover {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.hover DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/hover" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.hover DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/hover requests using function parameters")
    fun supports_sending_hover_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.hover(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/hover"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/hover request callback receiving a result using function parameters")
    fun supports_hover_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(0, result?.contents?.size)
            assertEquals(
                Range(start = Position(2u, 6u), end = Position(2u, 6u)),
                result?.range
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.hover {
                    Hover(
                        contents = listOf(),
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.hover DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/hover" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.hover DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/hover request callback receiving an error using function parameters")
    fun supports_hover_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.hover {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.hover DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/hover" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.hover DSL handler should have been called.")
    }

    // endregion
    // region textDocument/signatureHelp request

    @Test
    @DisplayName("supports textDocument/signatureHelp requests")
    fun supports_signature_help_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/signatureHelp"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.signatureHelp {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/signatureHelp", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    SignatureHelp(signatures = listOf())
                }
            }
        }

        assertEquals(true, called, "The textDocument.signatureHelp DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "signatures" to jsonArrayOf()
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/signatureHelp requests using parameter objects")
    fun supports_sending_signature_help_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.signatureHelp(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/signatureHelp"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/signatureHelp request callback receiving a result using parameter objects")
    fun supports_signature_help_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(0, result?.signatures?.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.signatureHelp {
                    SignatureHelp(signatures = listOf())
                }
            }
        }

        assertEquals(0, called, "The textDocument.signatureHelp DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/signatureHelp" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.signatureHelp DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/signatureHelp request callback receiving an error using parameter objects")
    fun supports_signature_help_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.signatureHelp {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.signatureHelp DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/signatureHelp" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.signatureHelp DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/signatureHelp requests using function parameters")
    fun supports_sending_signature_help_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.signatureHelp(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/signatureHelp"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/signatureHelp request callback receiving a result using function parameters")
    fun supports_signature_help_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(0, result?.signatures?.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.signatureHelp {
                    SignatureHelp(signatures = listOf())
                }
            }
        }

        assertEquals(0, called, "The textDocument.signatureHelp DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/signatureHelp" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.signatureHelp DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/signatureHelp request callback receiving an error using function parameters")
    fun supports_signature_help_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.signatureHelp {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.signatureHelp DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/signatureHelp" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.signatureHelp DSL handler should have been called.")
    }

    // endregion
    // region textDocument/definition request

    @Test
    @DisplayName("supports textDocument/definition requests")
    fun supports_definition_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/definition"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.definition {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/definition", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    GoTo()
                }
            }
        }

        assertEquals(true, called, "The textDocument.definition DSL should have been called.")

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
    @DisplayName("supports textDocument/definition requests returning a single location")
    fun supports_definition_requests_returning_a_single_location() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/definition"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.definition {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/definition", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    GoTo(Location(uri = uri, range = Range(start = position, end = position)))
                }
            }
        }

        assertEquals(true, called, "The textDocument.definition DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(2),
                            "character" to JsonPrimitive(6)
                        )
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/definition requests returning multiple locations")
    fun supports_definition_requests_returning_multiple_locations() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/definition"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.definition {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/definition", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    GoTo(
                        Location(uri = uri, range = Range(start = position, end = position)),
                        Location(uri = uri, range = Range(start = position, end = position))
                    )
                }
            }
        }

        assertEquals(true, called, "The textDocument.definition DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonArrayOf(
                    jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                        "range" to jsonObjectOf(
                            "start" to jsonObjectOf(
                                "line" to JsonPrimitive(2),
                                "character" to JsonPrimitive(6)
                            ),
                            "end" to jsonObjectOf(
                                "line" to JsonPrimitive(2),
                                "character" to JsonPrimitive(6)
                            )
                        )
                    ),
                    jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                        "range" to jsonObjectOf(
                            "start" to jsonObjectOf(
                                "line" to JsonPrimitive(2),
                                "character" to JsonPrimitive(6)
                            ),
                            "end" to jsonObjectOf(
                                "line" to JsonPrimitive(2),
                                "character" to JsonPrimitive(6)
                            )
                        )
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/definition requests using parameter objects")
    fun supports_sending_definition_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.definition(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/definition"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/definition request callback receiving a result using parameter objects")
    fun supports_definition_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.definition {
                    GoTo()
                }
            }
        }

        assertEquals(0, called, "The textDocument.definition DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/definition" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.definition DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/definition request callback receiving an error using parameter objects")
    fun supports_definition_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.definition {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.definition DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/definition" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.definition DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/definition requests using function parameters")
    fun supports_sending_definition_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.definition(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/definition"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/definition request callback receiving a result using function parameters")
    fun supports_definition_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.definition {
                    GoTo()
                }
            }
        }

        assertEquals(0, called, "The textDocument.definition DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/definition" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.definition DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/definition request callback receiving an error using function parameters")
    fun supports_definition_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.definition {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.definition DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/definition" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.definition DSL handler should have been called.")
    }

    // endregion
    // region textDocument/references request

    @Test
    @DisplayName("supports textDocument/references requests")
    fun supports_references_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/references"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    ),
                    "context" to jsonObjectOf(
                        "includeDeclaration" to JsonPrimitive(false)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.references {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/references", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    assertEquals(false, context.includeDeclaration)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.references DSL should have been called.")

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
    @DisplayName("supports sending textDocument/references requests using parameter objects")
    fun supports_sending_references_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.references(
            params = ReferenceParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u),
                context = ReferenceContext(
                    includeDeclaration = false
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/references"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    ),
                    "context" to jsonObjectOf(
                        "includeDeclaration" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/references request callback receiving a result using parameter objects")
    fun supports_references_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            params = ReferenceParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u),
                context = ReferenceContext(
                    includeDeclaration = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.references {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.references DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/references" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.references DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/references request callback receiving an error using parameter objects")
    fun supports_references_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            params = ReferenceParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u),
                context = ReferenceContext(
                    includeDeclaration = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.references {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.references DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/references" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.references DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/references requests using function parameters")
    fun supports_sending_references_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.references(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u),
            context = ReferenceContext(
                includeDeclaration = false
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/references"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    ),
                    "context" to jsonObjectOf(
                        "includeDeclaration" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/references request callback receiving a result using function parameters")
    fun supports_references_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u),
            context = ReferenceContext(
                includeDeclaration = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.references {
                    GoTo()
                }
            }
        }

        assertEquals(0, called, "The textDocument.references DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/references" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.references DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/references request callback receiving an error using function parameters")
    fun supports_references_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u),
            context = ReferenceContext(
                includeDeclaration = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.references {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.references DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/references" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.references DSL handler should have been called.")
    }

    // endregion
    // region textDocument/documentHighlight request

    @Test
    @DisplayName("supports textDocument/documentHighlight requests")
    fun supports_document_highlight_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentHighlight"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.documentHighlight {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/documentHighlight", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)
                    assertEquals(Position(2u, 6u), position)

                    DocumentHighlight(
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(true, called, "The textDocument.documentHighlight DSL should have been called.")

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
                            "character" to JsonPrimitive(6)
                        )
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/documentHighlight requests using parameter objects")
    fun supports_sending_document_highlight_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.documentHighlight(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentHighlight"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/documentHighlight request callback receiving a result using parameter objects")
    fun supports_document_highlight_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.documentHighlight(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(Position(2u, 6u), result?.range?.start)
            assertEquals(Position(2u, 6u), result?.range?.end)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.documentHighlight {
                    DocumentHighlight(
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentHighlight DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentHighlight" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentHighlight DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/documentHighlight request callback receiving an error using parameter objects")
    fun supports_document_highlight_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.documentHighlight(
            params = TextDocumentPositionParams(
                uri = "file:///home/lorem/ipsum.py",
                position = Position(2u, 6u)
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.documentHighlight {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentHighlight DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentHighlight" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentHighlight DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/documentHighlight requests using function parameters")
    fun supports_sending_document_highlight_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.documentHighlight(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentHighlight"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/documentHighlight request callback receiving a result using function parameters")
    fun supports_document_highlight_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.documentHighlight(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(Position(2u, 6u), result?.range?.start)
            assertEquals(Position(2u, 6u), result?.range?.end)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.documentHighlight {
                    DocumentHighlight(
                        range = Range(start = position, end = position)
                    )
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentHighlight DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentHighlight" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentHighlight DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/documentHighlight request callback receiving an error using function parameters")
    fun supports_document_highlight_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.documentHighlight(
            uri = "file:///home/lorem/ipsum.py",
            position = Position(2u, 6u)
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.documentHighlight {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentHighlight DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentHighlight" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentHighlight DSL handler should have been called.")
    }

    // endregion
    // region textDocument/documentSymbol request

    @Test
    @DisplayName("supports textDocument/documentSymbol requests")
    fun supports_document_symbol_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentSymbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.documentSymbol {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/documentSymbol", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.documentSymbol DSL should have been called.")

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
    @DisplayName("supports sending textDocument/documentSymbol requests using parameter objects")
    fun supports_sending_document_symbol_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.documentSymbol(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentSymbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/documentSymbol request callback receiving a result using parameter objects")
    fun supports_document_symbol_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.documentSymbol(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.documentSymbol {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentSymbol DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentSymbol" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentSymbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/documentSymbol request callback receiving an error using parameter objects")
    fun supports_document_symbol_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.documentSymbol(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.documentSymbol {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentSymbol DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentSymbol" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentSymbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/documentSymbol requests using function parameters")
    fun supports_sending_document_symbol_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.documentSymbol(
            uri = "file:///home/lorem/ipsum.py"
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/documentSymbol"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/documentSymbol request callback receiving a result using function parameters")
    fun supports_document_symbol_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.documentSymbol(
            uri = "file:///home/lorem/ipsum.py"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.documentSymbol {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentSymbol DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentSymbol" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentSymbol DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/documentSymbol request callback receiving an error using function parameters")
    fun supports_document_symbol_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.documentSymbol(
            uri = "file:///home/lorem/ipsum.py"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.documentSymbol {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.documentSymbol DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/documentSymbol" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.documentSymbol DSL handler should have been called.")
    }

    // endregion
    // region textDocument/codeAction request

    @Test
    @DisplayName("supports textDocument/codeAction requests")
    fun supports_code_action_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeAction"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "context" to jsonObjectOf(
                        "diagnostics" to jsonArrayOf()
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.codeAction {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/codeAction", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", textDocument.uri)

                    assertEquals(Position(5u, 12u), range.start)
                    assertEquals(Position(5u, 21u), range.end)

                    assertEquals(0, context.diagnostics.size)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.codeAction DSL should have been called.")

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
    @DisplayName("supports sending textDocument/codeAction requests using parameter objects")
    fun supports_sending_code_action_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.codeAction(
            params = CodeActionParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                context = CodeActionContext(
                    diagnostics = listOf()
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeAction"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "context" to jsonObjectOf(
                        "diagnostics" to jsonArrayOf()
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/codeAction request callback receiving a result using parameter objects")
    fun supports_code_action_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.codeAction(
            params = CodeActionParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                context = CodeActionContext(
                    diagnostics = listOf()
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.codeAction {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeAction DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeAction" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeAction DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/codeAction request callback receiving an error using parameter objects")
    fun supports_code_action_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.codeAction(
            params = CodeActionParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                context = CodeActionContext(
                    diagnostics = listOf()
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.codeAction {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeAction DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeAction" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeAction DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/codeAction requests using function parameters")
    fun supports_sending_code_action_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.codeAction(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            context = CodeActionContext(
                diagnostics = listOf()
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeAction"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "context" to jsonObjectOf(
                        "diagnostics" to jsonArrayOf()
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/codeAction request callback receiving a result using function parameters")
    fun supports_code_action_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.codeAction(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            context = CodeActionContext(
                diagnostics = listOf()
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.codeAction {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeAction DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeAction" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeAction DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/codeAction request callback receiving an error using function parameters")
    fun supports_code_action_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.codeAction(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            context = CodeActionContext(
                diagnostics = listOf()
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.codeAction {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeAction DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeAction" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeAction DSL handler should have been called.")
    }

    // endregion
    // region textDocument/codeLens request

    @Test
    @DisplayName("supports textDocument/codeLens requests")
    fun supports_code_lens_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeLens"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.codeLens {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/codeLens", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", uri)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.codeLens DSL should have been called.")

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
    @DisplayName("supports sending textDocument/codeLens requests using parameter objects")
    fun supports_sending_code_lens_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.codeLens(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeLens"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/codeLens request callback receiving a result using parameter objects")
    fun supports_code_lens_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.codeLens(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.codeLens {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeLens DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeLens" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeLens DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/codeLens request callback receiving an error using parameter objects")
    fun supports_code_lens_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.codeLens(
            params = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.codeLens {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeLens DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeLens" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeLens DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/codeLens requests using function parameters")
    fun supports_sending_code_lens_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.codeLens(
            uri = "file:///home/lorem/ipsum.py"
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/codeLens"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/codeLens request callback receiving a result using function parameters")
    fun supports_code_lens_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.codeLens(
            uri = "file:///home/lorem/ipsum.py"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.codeLens {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeLens DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeLens" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeLens DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/codeLens request callback receiving an error using function parameters")
    fun supports_code_lens_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.codeLens(
            uri = "file:///home/lorem/ipsum.py"
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.codeLens {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.codeLens DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/codeLens" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.codeLens DSL handler should have been called.")
    }

    // endregion
    // region textDocument/formatting request

    @Test
    @DisplayName("supports textDocument/formatting requests")
    fun supports_formatting_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/formatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.formatting {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/formatting", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", textDocument.uri)

                    assertEquals(2, options.size)
                    assertEquals(4u, options.tabSize)
                    assertEquals(false, options.insertSpaces)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.formatting DSL should have been called.")

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
    @DisplayName("supports sending textDocument/formatting requests using parameter objects")
    fun supports_sending_formatting_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.formatting(
            params = DocumentFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/formatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/formatting request callback receiving a result using parameter objects")
    fun supports_formatting_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.formatting(
            params = DocumentFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.formatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.formatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/formatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.formatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/formatting request callback receiving an error using parameter objects")
    fun supports_formatting_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.formatting(
            params = DocumentFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.formatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.formatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/formatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.formatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/formatting requests using function parameters")
    fun supports_sending_formatting_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.formatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/formatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/formatting request callback receiving a result using function parameters")
    fun supports_formatting_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.formatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.formatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.formatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/formatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.formatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/formatting request callback receiving an error using function parameters")
    fun supports_formatting_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.formatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.formatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.formatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/formatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.formatting DSL handler should have been called.")
    }

    // endregion
    // region textDocument/rangeFormatting request

    @Test
    @DisplayName("supports textDocument/rangeFormatting requests")
    fun supports_range_formatting_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rangeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.rangeFormatting {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/rangeFormatting", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", textDocument.uri)

                    assertEquals(Position(5u, 12u), range.start)
                    assertEquals(Position(5u, 21u), range.end)

                    assertEquals(2, options.size)
                    assertEquals(4u, options.tabSize)
                    assertEquals(false, options.insertSpaces)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.rangeFormatting DSL should have been called.")

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
    @DisplayName("supports sending textDocument/rangeFormatting requests using parameter objects")
    fun supports_sending_range_formatting_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.rangeFormatting(
            params = DocumentRangeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rangeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/rangeFormatting request callback receiving a result using parameter objects")
    fun supports_range_formatting_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.rangeFormatting(
            params = DocumentRangeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.rangeFormatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.rangeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rangeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rangeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/rangeFormatting request callback receiving an error using parameter objects")
    fun supports_range_formatting_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.rangeFormatting(
            params = DocumentRangeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                range = Range(
                    start = Position(5u, 12u),
                    end = Position(5u, 21u)
                ),
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.rangeFormatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.rangeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rangeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rangeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/rangeFormatting requests using function parameters")
    fun supports_sending_range_formatting_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.rangeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rangeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(12)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(21)
                        )
                    ),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/rangeFormatting request callback receiving a result using function parameters")
    fun supports_range_formatting_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.rangeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.rangeFormatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.rangeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rangeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rangeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/rangeFormatting request callback receiving an error using function parameters")
    fun supports_range_formatting_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.rangeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            range = Range(
                start = Position(5u, 12u),
                end = Position(5u, 21u)
            ),
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.rangeFormatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.rangeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rangeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rangeFormatting DSL handler should have been called.")
    }

    // endregion
    // region textDocument/onTypeFormatting request

    @Test
    @DisplayName("supports textDocument/onTypeFormatting requests")
    fun supports_on_type_formatting_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/onTypeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "ch" to JsonPrimitive("c"),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.onTypeFormatting {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/onTypeFormatting", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", textDocument.uri)
                    assertEquals(Position(5u, 12u), position)
                    assertEquals("c", ch)

                    assertEquals(2, options.size)
                    assertEquals(4u, options.tabSize)
                    assertEquals(false, options.insertSpaces)

                    listOf()
                }
            }
        }

        assertEquals(true, called, "The textDocument.onTypeFormatting DSL should have been called.")

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
    @DisplayName("supports sending textDocument/onTypeFormatting requests using parameter objects")
    fun supports_sending_on_type_formatting_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.onTypeFormatting(
            params = DocumentOnTypeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                ch = "c",
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/onTypeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "ch" to JsonPrimitive("c"),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/onTypeFormatting request callback receiving a result using parameter objects")
    fun supports_on_type_formatting_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.onTypeFormatting(
            params = DocumentOnTypeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                ch = "c",
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.onTypeFormatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.onTypeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/onTypeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.onTypeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/onTypeFormatting request callback receiving an error using parameter objects")
    fun supports_on_type_formatting_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.onTypeFormatting(
            params = DocumentOnTypeFormattingParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                ch = "c",
                options = FormattingOptions(
                    tabSize = 4u,
                    insertSpaces = false
                )
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.onTypeFormatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.onTypeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/onTypeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.onTypeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/onTypeFormatting requests using function parameters")
    fun supports_sending_on_type_formatting_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.onTypeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            ch = "c",
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/onTypeFormatting"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "ch" to JsonPrimitive("c"),
                    "options" to jsonObjectOf(
                        "tabSize" to JsonPrimitive(4),
                        "insertSpaces" to JsonPrimitive(false)
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/onTypeFormatting request callback receiving a result using function parameters")
    fun supports_on_type_formatting_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.onTypeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            ch = "c",
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.onTypeFormatting {
                    listOf()
                }
            }
        }

        assertEquals(0, called, "The textDocument.onTypeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/onTypeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.onTypeFormatting DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/onTypeFormatting request callback receiving an error using function parameters")
    fun supports_on_type_formatting_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.onTypeFormatting(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            ch = "c",
            options = FormattingOptions(
                tabSize = 4u,
                insertSpaces = false
            )
        ) {
            ++called

            assertEquals(0, result.size)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.onTypeFormatting {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.onTypeFormatting DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/onTypeFormatting" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.onTypeFormatting DSL handler should have been called.")
    }

    // endregion
    // region textDocument/rename request

    @Test
    @DisplayName("supports textDocument/rename requests")
    fun supports_rename_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rename"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "newName" to JsonPrimitive("dolor")
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                textDocument.rename {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("textDocument/rename", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals("file:///home/lorem/ipsum.py", textDocument.uri)
                    assertEquals(Position(5u, 12u), position)
                    assertEquals("dolor", newName)

                    WorkspaceEdit(changes = mapOf())
                }
            }
        }

        assertEquals(true, called, "The textDocument.rename DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "changes" to jsonObjectOf()
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending textDocument/rename requests using parameter objects")
    fun supports_sending_rename_requests_using_parameter_objects() = testJsonRpc {
        val id = client.textDocument.rename(
            params = RenameParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                newName = "dolor"
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rename"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "newName" to JsonPrimitive("dolor")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/rename request callback receiving a result using parameter objects")
    fun supports_rename_request_callback_receiving_a_result_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.rename(
            params = RenameParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                newName = "dolor"
            )
        ) {
            ++called

            assertEquals(0, result?.changes?.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.rename {
                    WorkspaceEdit(changes = mapOf())
                }
            }
        }

        assertEquals(0, called, "The textDocument.rename DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rename" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rename DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/rename request callback receiving an error using parameter objects")
    fun supports_rename_request_callback_receiving_an_error_using_parameter_objects() = testJsonRpc {
        var called = 0

        client.textDocument.rename(
            params = RenameParams(
                textDocument = TextDocumentIdentifier(
                    uri = "file:///home/lorem/ipsum.py"
                ),
                position = Position(5u, 12u),
                newName = "dolor"
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.rename {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.rename DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rename" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rename DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending textDocument/rename requests using function parameters")
    fun supports_sending_rename_requests_using_function_parameters() = testJsonRpc {
        val id = client.textDocument.rename(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            newName = "dolor"
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("textDocument/rename"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "textDocument" to jsonObjectOf(
                        "uri" to JsonPrimitive("file:///home/lorem/ipsum.py")
                    ),
                    "position" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(12)
                    ),
                    "newName" to JsonPrimitive("dolor")
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports textDocument/rename request callback receiving a result using function parameters")
    fun supports_rename_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.rename(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            newName = "dolor"
        ) {
            ++called

            assertEquals(0, result?.changes?.size)

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                textDocument.rename {
                    WorkspaceEdit(changes = mapOf())
                }
            }
        }

        assertEquals(0, called, "The textDocument.rename DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rename" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rename DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports textDocument/rename request callback receiving an error using function parameters")
    fun supports_rename_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.textDocument.rename(
            textDocument = TextDocumentIdentifier(
                uri = "file:///home/lorem/ipsum.py"
            ),
            position = Position(5u, 12u),
            newName = "dolor"
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                textDocument.rename {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The textDocument.rename DSL handler should not have been called.")
        client.jsonRpc {} // The "textDocument/rename" response is processed by the handler callback.
        assertEquals(1, called, "The textDocument.rename DSL handler should have been called.")
    }

    // endregion
}
