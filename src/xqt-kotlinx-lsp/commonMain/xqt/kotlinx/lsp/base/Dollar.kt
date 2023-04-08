// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import xqt.kotlinx.rpc.json.protocol.Notification
import kotlin.jvm.JvmInline

/**
 * A notification in the `dollar/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @param notification the underlying notification message
 *
 * @since 2.0.0
 */
@JvmInline
value class DollarNotification(val notification: Notification) {
    companion object {
        /**
         * Cancel an active request.
         *
         * A request that got canceled still needs to return from the server and send a response
         * back. It can not be left open/hanging. This is in line with the JSON RPC protocol that
         * requires that every request sends a response back. In addition, it allows for returning
         * partial results on cancel.
         */
        const val CANCEL_REQUEST: String = "$/cancelRequest"
    }
}

/**
 * A notification in the `dollar/` namespace.
 *
 * Note that this does not check the namespace. Therefore, method DSLs need to
 * check the fully qualified method name.
 *
 * @since 2.0.0
 */
val Notification.dollar: DollarNotification
    get() = DollarNotification(this)
