// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.io.*
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer

/**
 * Handle LSP JSON-RPC header and content parts.
 *
 * The LSP specifies that JSON elements have an HTTP-like header section with a
 * `Content-Length` and optional `Content-Type` header.
 */
class LspJsonRpcServer(
    private val input: BinaryInputChannel,
    private val output: BinaryOutputChannel
) : JsonRpcServer() {
    override fun send(message: JsonElement) {
        val body = message.toString().encodeToByteArray()
        output.writeUtf8String("Content-Length: ${body.size}", lineEnding = LineEnding.CrLf)
        output.writeUtf8String("", lineEnding = LineEnding.CrLf)
        output.writeBytes(body)
        output.flush()
    }

    override fun receive(): JsonElement? {
        var contentLength = -1

        var line = input.readUtf8Line(LineEnding.CrLf) ?: return null
        while (line != "") {
            if (line.startsWith("Content-Length: ")) {
                val length = line.substringAfter("Content-Length: ")
                contentLength = length.toIntOrNull() ?: -1
            }
            line = input.readUtf8Line(LineEnding.CrLf) ?: return null
        }

        if (contentLength == -1) {
            return null
        }

        val body = input.readBytes(contentLength)
        return when {
            body.size != contentLength -> null
            else -> Json.parseToJsonElement(body.decodeToString())
        }
    }

    override fun close() {
        input.close()
        output.close()
    }
}
