// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.sendResult

private const val SHUTDOWN = "shutdown"

/**
 * Asks the server to shut down, but to not exit.
 *
 * The shutdown request is sent from the client to the server. The server must
 * not exit, otherwise the response might not be delivered correctly to the
 * client.
 *
 * There is a separate exit notification that asks the server to exit.
 *
 * @since 1.0.0
 */
fun RequestMessage.shutdown(handler: () -> Unit) {
    if (method == SHUTDOWN) {
        handler()
        sendResult(null)
    }
}
