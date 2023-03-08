// Copyright (C) 2022-2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import kotlin.test.assertEquals

data class TestJsonRpcServer(
    private val input: MutableList<JsonElement>,
    private val output: MutableList<JsonElement>
) : JsonRpcServer() {
    override fun send(message: JsonElement) {
        output.add(message)
    }

    override fun receive(): JsonElement? = input.removeFirstOrNull()

    override fun close() {
    }
}

data class TestClientServer(val client: JsonRpcServer, val server: JsonRpcServer) {
    constructor(input: MutableList<JsonElement>, output: MutableList<JsonElement>) :
            this(TestJsonRpcServer(input, output), TestJsonRpcServer(output, input))
}

fun testJsonRpc(handler: TestClientServer.() -> Unit) {
    val input = mutableListOf<JsonElement>()
    val output = mutableListOf<JsonElement>()

    val test = TestClientServer(input, output)
    test.handler()

    assertEquals(null, test.client.receive())
    assertEquals(null, test.server.receive())
}
