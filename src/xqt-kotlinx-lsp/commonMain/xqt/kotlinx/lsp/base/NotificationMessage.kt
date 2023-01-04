// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import xqt.kotlinx.rpc.json.protocol.Notification

/**
 * A notification message.
 *
 * A processed notification message must not send a response back. They work like events.
 *
 * @since 1.0.0
 */
typealias NotificationMessage = Notification
