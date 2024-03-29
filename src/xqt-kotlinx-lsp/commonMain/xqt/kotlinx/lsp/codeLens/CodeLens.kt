// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.codeLens

import xqt.kotlinx.lsp.base.RequestMessage
import xqt.kotlinx.rpc.json.protocol.JsonRpcServer
import kotlin.jvm.JvmInline

/**
 * A request in the `codeLens/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param request the underlying request message
 *
 * @since 1.0.0
 */
@JvmInline
value class CodeLensRequest(val request: RequestMessage) {
    companion object {
        /**
         * The code lens resolve request is sent from the client to the server to resolve the
         * command for a given code lens item.
         */
        const val RESOLVE: String = "codeLens/resolve"
    }
}

/**
 * A method in the `codeLens/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @param server the underlying JSON-RPC server
 *
 * @since 1.0.0
 */
@JvmInline
value class CodeLensJsonRpcServer(val server: JsonRpcServer)

/**
 * A request in the `codeLens/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 1.0.0
 */
val RequestMessage.codeLens: CodeLensRequest
    get() = CodeLensRequest(this)

/**
 * A method in the `codeLens/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method wrappers need
 * to specify the fully qualified method name.
 *
 * @since 1.0.0
 */
val JsonRpcServer.codeLens: CodeLensJsonRpcServer
    get() = CodeLensJsonRpcServer(this)
