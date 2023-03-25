// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
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

    // endregion
}
