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
    @DisplayName("supports sending textDocument/didOpen notifications using DidOpenTextDocumentParams")
    fun supports_sending_did_open_notifications_using_class_params() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/didChange notifications using DidChangeTextDocumentParams")
    fun supports_sending_did_change_notifications_using_class_params() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/didClose notifications using TextDocumentIdentifier")
    fun supports_sending_did_close_notifications_using_class_params() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/publishDiagnostics notifications using TextDocumentIdentifier")
    fun supports_sending_publish_diagnostics_notifications_using_class_params() = testJsonRpc {
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
    @DisplayName("supports textDocument/completion requests returning a CompletionItem[]")
    fun supports_completion_requests_returning_a_completion_item_array() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/completion requests using TextDocumentPosition")
    fun supports_sending_completion_requests_using_text_document_position() = testJsonRpc {
        val id = client.textDocument.completion(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/completion request callback receiving CompletionResponse using params object")
    fun supports_completion_request_callback_receiving_completion_response_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/completion request callback receiving ErrorObject using params object")
    fun supports_completion_request_callback_receiving_error_object_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.completion(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/completion request callback receiving CompletionResponse")
    fun supports_completion_request_callback_receiving_completion_response() = testJsonRpc {
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
    @DisplayName("supports textDocument/completion request callback receiving ErrorObject")
    fun supports_completion_request_callback_receiving_error_object() = testJsonRpc {
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
    @DisplayName("supports textDocument/hover requests returning a Hover object")
    fun supports_hover_requests_returning_a_hover_object() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/hover requests using TextDocumentPosition")
    fun supports_sending_hover_requests_using_text_document_position() = testJsonRpc {
        val id = client.textDocument.hover(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/hover request callback receiving Hover using params object")
    fun supports_hover_request_callback_receiving_hover_response_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/hover request callback receiving ErrorObject using params object")
    fun supports_hover_request_callback_receiving_error_object_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.hover(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/hover request callback receiving HoverResponse")
    fun supports_hover_request_callback_receiving_hover_response() = testJsonRpc {
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
    @DisplayName("supports textDocument/hover request callback receiving ErrorObject")
    fun supports_hover_request_callback_receiving_error_object() = testJsonRpc {
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
    @DisplayName("supports textDocument/signatureHelp requests returning a SignatureHelp object")
    fun supports_signature_help_requests_returning_a_signature_help_object() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/signatureHelp requests using TextDocumentPosition")
    fun supports_sending_signature_help_requests_using_text_document_position() = testJsonRpc {
        val id = client.textDocument.signatureHelp(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/signatureHelp request callback receiving Hover using params object")
    fun supports_signature_help_request_callback_receiving_hover_response_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/signatureHelp request callback receiving ErrorObject using params object")
    fun supports_signature_help_request_callback_receiving_error_object_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.signatureHelp(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/signatureHelp request callback receiving HoverResponse")
    fun supports_signature_help_request_callback_receiving_hover_response() = testJsonRpc {
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
    @DisplayName("supports textDocument/signatureHelp request callback receiving ErrorObject")
    fun supports_signature_help_request_callback_receiving_error_object() = testJsonRpc {
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
    @DisplayName("supports textDocument/definition requests returning no locations")
    fun supports_definition_requests_returning_no_locations() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/definition requests using TextDocumentPosition")
    fun supports_sending_definition_requests_using_text_document_position() = testJsonRpc {
        val id = client.textDocument.definition(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/definition request callback receiving Hover using params object")
    fun supports_definition_request_callback_receiving_hover_response_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/definition request callback receiving ErrorObject using params object")
    fun supports_definition_request_callback_receiving_error_object_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.definition(
            params = TextDocumentPosition(
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
    @DisplayName("supports textDocument/definition request callback receiving HoverResponse")
    fun supports_definition_request_callback_receiving_hover_response() = testJsonRpc {
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
    @DisplayName("supports textDocument/definition request callback receiving ErrorObject")
    fun supports_definition_request_callback_receiving_error_object() = testJsonRpc {
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
    @DisplayName("supports textDocument/references requests returning a locations array")
    fun supports_references_requests_returning_a_locations_array() = testJsonRpc {
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
    @DisplayName("supports sending textDocument/references requests using ReferencePrams")
    fun supports_sending_references_requests_using_reference_params() = testJsonRpc {
        val id = client.textDocument.references(
            params = ReferencesParams(
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
    @DisplayName("supports textDocument/references request callback receiving a Location list using params object")
    fun supports_references_request_callback_receiving_a_location_list_response_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            params = ReferencesParams(
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
    @DisplayName("supports textDocument/references request callback receiving ErrorObject using params object")
    fun supports_references_request_callback_receiving_error_object_from_params_object() = testJsonRpc {
        var called = 0

        client.textDocument.references(
            params = ReferencesParams(
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
    @DisplayName("supports textDocument/references request callback receiving ReferencesResponse")
    fun supports_references_request_callback_receiving_references_response() = testJsonRpc {
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
    @DisplayName("supports textDocument/references request callback receiving ErrorObject")
    fun supports_references_request_callback_receiving_error_object() = testJsonRpc {
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
}
