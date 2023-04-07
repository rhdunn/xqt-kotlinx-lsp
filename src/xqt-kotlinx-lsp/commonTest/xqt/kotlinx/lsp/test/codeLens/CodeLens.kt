// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.codeLens

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.codeLens.codeLens
import xqt.kotlinx.lsp.codeLens.resolve
import xqt.kotlinx.lsp.test.base.testJsonRpc
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

    // endregion
}
