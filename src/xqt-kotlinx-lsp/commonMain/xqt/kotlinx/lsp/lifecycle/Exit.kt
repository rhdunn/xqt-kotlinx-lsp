// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import xqt.kotlinx.lsp.base.NotificationMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification

/**
 * A notification to ask the server to exit its process.
 *
 * The server should exit with `success` code 0 if the shutdown request has been received
 * before; otherwise with `error` code 1.
 *
 * @since 1.0.0
 */
fun NotificationMessage.exit(handler: () -> Unit): Unit = method(
    method = LifecycleNotification.EXIT,
    handler = handler
)

/**
 * A notification to ask the server to exit its process.
 *
 * The server should exit with `success` code 0 if the shutdown request has been received
 * before; otherwise with `error` code 1.
 *
 * @since 1.0.0
 */
fun JsonRpcServer.exit(): Unit = sendNotification(LifecycleNotification.EXIT)
