// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A request in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 1.0.0
 */
@JvmInline
value class TextDocumentRequest(val request: RequestMessage) {
    companion object {
        /**
         * The code action request is sent from the client to the server to compute commands for
         * a given text document and range.
         *
         * The request is trigger when the user moves the cursor into an problem marker in the
         * editor or presses the lightbulb associated with a marker.
         */
        const val CODE_ACTION: String = "textDocument/codeAction"

        /**
         * The completion request is sent from the client to the server to compute completion items
         * at a given cursor position.
         *
         * If computing complete completion items is expensive, servers can additionally provide a
         * handler for the resolve completion item request. This request is sent when a completion
         * item is selected in the user interface.
         */
        const val COMPLETION: String = "textDocument/completion"

        /**
         * The goto definition request is sent from the client to the server to resolve the definition
         * location of a symbol at a given text document position.
         */
        const val DEFINITION: String = "textDocument/definition"

        /**
         * The document highlight request is sent from the client to the server to resolve the document
         * highlights for a given text document position.
         */
        const val DOCUMENT_HIGHLIGHT: String = "textDocument/documentHighlight"

        /**
         * The document symbol request is sent from the client to the server to list all symbols
         * found in a given text document.
         */
        const val DOCUMENT_SYMBOL: String = "textDocument/documentSymbol"

        /**
         * The hover request is sent from the client to the server to request hover information at
         * a given text document position.
         */
        const val HOVER: String = "textDocument/hover"

        /**
         * The references request is sent from the client to the server to resolve project-wide
         * references for the symbol denoted by the given text document position.
         */
        const val REFERENCES: String = "textDocument/references"

        /**
         * The signature help request is sent from the client to the server to request signature
         * information at a given cursor position.
         */
        const val SIGNATURE_HELP: String = "textDocument/signatureHelp"
    }
}

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

        /**
         * The diagnostics notification is sent from the server to the client to signal results of
         * validation runs.
         */
        const val PUBLISH_DIAGNOSTICS: String = "textDocument/publishDiagnostics"
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
 * A request in the `textDocument/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val RequestMessage.textDocument: TextDocumentRequest
    get() = TextDocumentRequest(this)

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
