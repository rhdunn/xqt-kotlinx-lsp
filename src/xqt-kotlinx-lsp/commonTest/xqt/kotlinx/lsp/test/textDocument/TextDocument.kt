// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Text Document DSL")
class TextDocumentDSL {
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
}
