// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import xqt.kotlinx.lsp.base.NotificationMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification

/**
 * A notification to ask the server to exit its process.
 *
 * @since 1.0.0
 */
fun NotificationMessage.exit(handler: () -> Unit): Unit = method(
    method = LifecycleNotification.EXIT,
    handler = handler
)

/**
 * Send an exit notification to the server.
 *
 * @since 1.0.0
 */
fun JsonRpcServer.exit(): Unit = sendNotification(LifecycleNotification.EXIT)
