// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A notification in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param notification the underlying notification message
 *
 * @since 1.0.0
 */
@JvmInline
value class WindowNotification(val notification: Notification)

/**
 * A method in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @param server the underlying JSON-RPC server
 *
 * @since 1.0.0
 */
@JvmInline
value class WindowJsonRpcServer(val server: JsonRpcServer)

/**
 * A notification in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val Notification.window: WindowNotification
    get() = WindowNotification(this)

/**
 * A method in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @since 1.0.0
 */
val JsonRpcServer.window: WindowJsonRpcServer
    get() = WindowJsonRpcServer(this)
