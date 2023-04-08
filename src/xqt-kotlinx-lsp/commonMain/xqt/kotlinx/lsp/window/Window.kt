// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A request in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 2.0.0
 */
@JvmInline
value class WindowRequest(val request: RequestMessage) {
    companion object {
        /**
         * Ask the client to display a particular message in the user interface.
         *
         * In addition to the show message notification the request allows to pass actions
         * and to wait for an answer from the client.
         */
        const val SHOW_MESSAGE: String = "window/showMessageRequest"
    }
}

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
value class WindowNotification(val notification: Notification) {
    companion object {
        /**
         * Ask the client to log a particular message.
         */
        const val LOG_MESSAGE: String = "window/logMessage"

        /**
         * Ask the client to display a particular message in the user interface.
         */
        const val SHOW_MESSAGE: String = "window/showMessage"
    }
}

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
 * A request in the `window/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 2.0.0
 */
val RequestMessage.window: WindowRequest
    get() = WindowRequest(this)

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
