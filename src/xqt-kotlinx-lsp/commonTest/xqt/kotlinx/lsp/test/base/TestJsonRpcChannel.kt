// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.protocol.JsonRpcChannel

class TestJsonRpcChannel : JsonRpcChannel {
    val input = mutableListOf<JsonElement>()
    val output = mutableListOf<JsonElement>()

    override fun send(message: JsonElement) {
        output.add(message)
    }

    override fun receive(): JsonElement? = input.removeFirstOrNull()

    override fun close() {
    }
}
