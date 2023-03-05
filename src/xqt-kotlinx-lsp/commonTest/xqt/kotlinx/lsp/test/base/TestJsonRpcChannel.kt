// Copyright (C) 2022-2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.protocol.JsonRpcChannel
import kotlin.test.assertEquals

data class TestJsonRpcChannel(
    private val input: MutableList<JsonElement>,
    private val output: MutableList<JsonElement>
) : JsonRpcChannel {
    override fun send(message: JsonElement) {
        output.add(message)
    }

    override fun receive(): JsonElement? = input.removeFirstOrNull()

    override fun close() {
    }
}

data class TestClientServer(val client: JsonRpcChannel, val server: JsonRpcChannel) {
    constructor(input: MutableList<JsonElement>, output: MutableList<JsonElement>) :
            this(TestJsonRpcChannel(input, output), TestJsonRpcChannel(output, input))
}

fun testJsonRpc(handler: TestClientServer.() -> Unit) {
    val input = mutableListOf<JsonElement>()
    val output = mutableListOf<JsonElement>()

    val test = TestClientServer(input, output)
    test.handler()

    assertEquals(null, test.client.receive())
    assertEquals(null, test.server.receive())
}
