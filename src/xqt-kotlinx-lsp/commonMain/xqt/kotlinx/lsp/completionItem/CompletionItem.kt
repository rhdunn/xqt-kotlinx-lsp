// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.completionItem

import xqt.kotlinx.lsp.base.RequestMessage
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
value class CompletionItemRequest(val request: RequestMessage)

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
