// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.CancelParams
import xqt.kotlinx.lsp.base.cancelRequest
import xqt.kotlinx.lsp.base.dollar
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Text Dollar DSL")
class TextDollarDSL {
    // region $/cancelRequest notification

    @Test
    @DisplayName("supports $/cancelRequest notifications")
    fun supports_cancel_request_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("$/cancelRequest"),
                "params" to jsonObjectOf(
                    "id" to JsonPrimitive(1234)
                )
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                dollar.cancelRequest {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("$/cancelRequest", method)

                    assertEquals(JsonIntOrString.IntegerValue(1234), id)
                }
            }
        }

        assertEquals(true, called, "The dollar.cancelRequest DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending $/cancelRequest notifications using parameter objects")
    fun supports_sending_cancel_request_notifications_using_parameter_objects() = testJsonRpc {
        server.dollar.cancelRequest(
            params = CancelParams(
                id = JsonIntOrString.IntegerValue(1234)
            )
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("$/cancelRequest"),
                "params" to jsonObjectOf(
                    "id" to JsonPrimitive(1234)
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending $/cancelRequest notifications using function parameters")
    fun supports_sending_cancel_request_notifications_using_function_parameters() = testJsonRpc {
        server.dollar.cancelRequest(
            id = JsonIntOrString.IntegerValue(1234)
        )

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("$/cancelRequest"),
                "params" to jsonObjectOf(
                    "id" to JsonPrimitive(1234)
                )
            ),
            client.receive()
        )
    }

    // endregion
}
