// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * The goto definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.definition(handler: TextDocumentPosition.() -> List<Location>) {
    if (request.method == TextDocumentRequest.DEFINITION) {
        val result = request.params(TextDocumentPosition).handler()
        when (result.size) {
            1 -> request.sendResult(result[0], Location)
            else -> request.sendResult(result, JsonTypedArray(Location))
        }
    }
}
