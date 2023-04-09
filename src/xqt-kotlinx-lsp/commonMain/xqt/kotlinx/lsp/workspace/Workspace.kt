// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A request in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 1.0.0
 */
@JvmInline
value class WorkspaceRequest(val request: RequestMessage) {
    companion object {
        /**
         * The workspace symbol request is sent from the client to the server to list project-wide
         * symbols matching the query string.
         */
        const val SYMBOL: String = "workspace/symbol"
    }
}

/**
 * A notification in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param notification the underlying notification message
 *
 * @since 1.0.0
 */
@JvmInline
value class WorkspaceNotification(val notification: Notification) {
    companion object {
        /**
         * Ask the client to display a particular message in the user interface.
         */
        const val DID_CHANGE_CONFIGURATION: String = "workspace/didChangeConfiguration"

        /**
         * The watched files notification is sent from the client to the server when
         * the client detects changes to files watched by the language client.
         */
        const val DID_CHANGE_WATCHED_FILES: String = "workspace/didChangeWatchedFiles"
    }
}

/**
 * A method in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @param server the underlying JSON-RPC server
 *
 * @since 1.0.0
 */
@JvmInline
value class WorkspaceJsonRpcServer(val server: JsonRpcServer)

/**
 * A request in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val RequestMessage.workspace: WorkspaceRequest
    get() = WorkspaceRequest(this)

/**
 * A notification in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val Notification.workspace: WorkspaceNotification
    get() = WorkspaceNotification(this)

/**
 * A method in the `workspace/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @since 1.0.0
 */
val JsonRpcServer.workspace: WorkspaceJsonRpcServer
    get() = WorkspaceJsonRpcServer(this)
