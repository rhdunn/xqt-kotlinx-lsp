// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.codeLens

import xqt.kotlinx.lsp.textDocument.CodeLens
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult

/**
 * The code lens resolve request is sent from the client to the server to resolve the
 * command for a given code lens item.
 *
 * @since 1.0.0
 */
fun CodeLensRequest.resolve(handler: CodeLens.() -> CodeLens) {
    if (request.method == CodeLensRequest.RESOLVE) {
        val result = request.params(CodeLens).handler()
        request.sendResult(result, CodeLens)
    }
}
