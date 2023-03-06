// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.misc

import xqt.kotlinx.lsp.base.LspJsonRpcServer
import xqt.kotlinx.rpc.json.io.BinaryInputChannel
import xqt.kotlinx.rpc.json.io.BinaryOutputChannel
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer

/**
 * Use stdio as the communication channel.
 */
fun stdio(): JsonRpcServer? {
    val input = BinaryInputChannel.stdin ?: return null
    val output = BinaryOutputChannel.stdout ?: return null
    return LspJsonRpcServer(input, output)
}
