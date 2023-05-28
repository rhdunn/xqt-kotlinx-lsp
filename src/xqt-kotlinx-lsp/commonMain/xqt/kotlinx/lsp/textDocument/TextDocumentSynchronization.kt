// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonIntEnumerationType
import kotlin.jvm.JvmInline

/**
 * Defines how the host (editor) should sync document changes to the language server.
 *
 * @param kind the text document sync kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class TextDocumentSyncKind(override val kind: Int) : JsonEnumeration<Int> {
    companion object : JsonIntEnumerationType<TextDocumentSyncKind>() {
        override fun valueOf(value: Int): TextDocumentSyncKind = TextDocumentSyncKind(value)

        /**
         * Documents should not be synced at all.
         */
        val None: TextDocumentSyncKind = TextDocumentSyncKind(0)

        /**
         * Documents are synced by always sending the full content of the document.
         */
        val Full: TextDocumentSyncKind = TextDocumentSyncKind(1)

        /**
         * Documents are synced by sending the full content on open. After that only incremental
         * updates to the document are sent.
         */
        val Incremental: TextDocumentSyncKind = TextDocumentSyncKind(2)
    }
}
