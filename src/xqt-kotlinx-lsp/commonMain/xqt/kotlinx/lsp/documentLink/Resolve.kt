// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.documentLink

import xqt.kotlinx.lsp.textDocument.DocumentLink
import xqt.kotlinx.rpc.json.protocol.*

/**
 * The request is sent from the client to the server to resolve additional information for
 * a given completion item.
 *
 * @since 2.0.0
 */
fun DocumentLinkRequest.resolve(
    handler: DocumentLink.() -> DocumentLink
): Unit = request.method(
    method = DocumentLinkRequest.RESOLVE,
    handler = handler,
    paramsSerializer = DocumentLink,
    resultSerializer = DocumentLink
)
