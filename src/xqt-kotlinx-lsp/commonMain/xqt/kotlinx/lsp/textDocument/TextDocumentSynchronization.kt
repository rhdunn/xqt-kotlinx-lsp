// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

/**
 * Defines how the host (editor) should sync document changes to the language server.
 *
 * @param kind the text document sync kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class TextDocumentSyncKind(val kind: Int) {
    companion object : JsonSerialization<TextDocumentSyncKind> {
        override fun serializeToJson(value: TextDocumentSyncKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): TextDocumentSyncKind {
            return TextDocumentSyncKind(JsonInt.deserialize(json))
        }

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
         * updates to the document are send.
         */
        val Incremental: TextDocumentSyncKind = TextDocumentSyncKind(2)
    }
}
