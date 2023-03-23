// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A notification in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param notification the underlying notification message
 *
 * @since 1.0.0
 */
@JvmInline
value class TextDocumentNotification(val notification: Notification) {
    companion object {
        /**
         * The document change notification is sent from the client to the server to signal changes
         * to a text document.
         */
        const val DID_CHANGE: String = "textDocument/didChange"

        /**
         * The document close notification is sent from the client to the server when the document
         * got closed in the client.
         *
         * The document's master now exists where the document's URI points to (e.g. if the document's
         * URI is a file uri the master now exists on disk).
         */
        const val DID_CLOSE: String = "textDocument/didClose"

        /**
         * The document open notification is sent from the client to the server to signal newly
         * opened text documents.
         *
         * The document's content is now managed by the client and the server must not try to read
         * the document's content using the document's uri.
         */
        const val DID_OPEN: String = "textDocument/didOpen"
    }
}

/**
 * A method in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @param server the underlying JSON-RPC server
 *
 * @since 1.0.0
 */
@JvmInline
value class TextDocumentJsonRpcServer(val server: JsonRpcServer)

/**
 * A notification in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val Notification.textDocument: TextDocumentNotification
    get() = TextDocumentNotification(this)

/**
 * A method in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @since 1.0.0
 */
val JsonRpcServer.textDocument: TextDocumentJsonRpcServer
    get() = TextDocumentJsonRpcServer(this)