// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.completionItem

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.completionItem.completionItem
import xqt.kotlinx.lsp.completionItem.resolve
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.*
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Completion Item DSL")
class CompletionItemDSL {
    // region completionItem/resolve request

    @Test
    @DisplayName("supports completionItem/resolve requests returning a CompletionItem")
    fun supports_resolve_requests_returning_a_completion_item_array() = testJsonRpc {
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

    // endregion
}
