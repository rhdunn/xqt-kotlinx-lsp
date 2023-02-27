// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

import xqt.kotlinx.lsp.base.NotificationMessage

private const val EXIT = "exit"

/**
 * A notification to ask the server to exit its process.
 *
 * @since 1.0.0
 */
fun NotificationMessage.exit(handler: () -> Unit) {
    if (method == EXIT) {
        handler()
    }
}
