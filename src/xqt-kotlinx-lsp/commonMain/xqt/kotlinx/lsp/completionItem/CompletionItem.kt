// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.completionItem

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import kotlin.jvm.JvmInline

/**
 * A request in the `completionItem/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 1.0.0
 */
@JvmInline
value class CompletionItemRequest(val request: RequestMessage) {
    companion object {
        /**
         * The request is sent from the client to the server to resolve additional information for
         * a given completion item.
         */
        const val RESOLVE: String = "completionItem/resolve"
    }
}

/**
 * A method in the `completionItem/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @param server the underlying JSON-RPC server
 *
 * @since 1.0.0
 */
@JvmInline
value class CompletionItemJsonRpcServer(val server: JsonRpcServer)

/**
 * A request in the `completionItem/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val RequestMessage.completionItem: CompletionItemRequest
    get() = CompletionItemRequest(this)

/**
 * A method in the `completionItem/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @since 1.0.0
 */
val JsonRpcServer.completionItem: CompletionItemJsonRpcServer
    get() = CompletionItemJsonRpcServer(this)
