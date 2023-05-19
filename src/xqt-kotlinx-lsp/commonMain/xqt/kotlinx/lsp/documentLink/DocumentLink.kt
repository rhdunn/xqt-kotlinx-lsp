// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.documentLink

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import kotlin.jvm.JvmInline

/**
 * A request in the `documentLink/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 2.0.0
 */
@JvmInline
value class DocumentLinkRequest(val request: RequestMessage) {
    companion object {
        /**
         * The document link resolve request is sent from the client to the server
         * to resolve the target of a given document link.
         */
        const val RESOLVE: String = "documentLink/resolve"
    }
}

/**
 * A request in the `documentLink/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 2.0.0
 */
val RequestMessage.documentLink: DocumentLinkRequest
    get() = DocumentLinkRequest(this)
