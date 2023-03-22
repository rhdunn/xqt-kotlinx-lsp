// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.lsp.base.ResponseMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.sendRequest
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString

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

/**
 * Send a shutdown request to the server.
 *
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun JsonRpcServer.shutdown(responseHandler: (ResponseMessage.() -> Unit)? = null): JsonIntOrString = sendRequest(
    method = SHUTDOWN,
    responseHandler = responseHandler
)
