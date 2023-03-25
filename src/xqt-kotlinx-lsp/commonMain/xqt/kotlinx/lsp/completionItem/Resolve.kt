// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.completionItem

import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.rpc.json.protocol.*

/**
 * The request is sent from the client to the server to resolve additional information for
 * a given completion item.
 *
 * @since 1.0.0
 */
fun CompletionItemRequest.resolve(handler: CompletionItem.() -> CompletionItem) {
    if (request.method == CompletionItemRequest.RESOLVE) {
        val result = request.params(CompletionItem).handler()
        request.sendResult(result, CompletionItem)
    }
}
