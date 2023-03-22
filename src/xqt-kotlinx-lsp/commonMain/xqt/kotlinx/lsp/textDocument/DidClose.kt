// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.params

/**
 * The document close notification is sent from the client to the server when the document
 * got closed in the client.
 *
 * The document's master now exists where the document's URI points to (e.g. if the document's
 * URI is a file uri the master now exists on disk).
 *
 * @since 1.0.0
 */
fun TextDocumentNotification.didClose(handler: TextDocumentIdentifier.() -> Unit) {
    if (notification.method == TextDocumentNotification.DID_CLOSE) {
        notification.params(TextDocumentIdentifier).handler()
    }
}
