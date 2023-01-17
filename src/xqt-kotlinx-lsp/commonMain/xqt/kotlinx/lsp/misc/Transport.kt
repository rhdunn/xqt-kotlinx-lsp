// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.misc

import xqt.kotlinx.lsp.base.LspJsonRpcChannel
import xqt.kotlinx.rpc.json.io.BinaryInputChannel
import xqt.kotlinx.rpc.json.io.BinaryOutputChannel
import xqt.kotlinx.rpc.json.protocol.JsonRpcChannel

/**
 * Use stdio as the communication channel.
 */
fun stdio(): JsonRpcChannel? {
    val input = BinaryInputChannel.stdin ?: return null
    val output = BinaryOutputChannel.stdout ?: return null
    return LspJsonRpcChannel(input, output)
}
