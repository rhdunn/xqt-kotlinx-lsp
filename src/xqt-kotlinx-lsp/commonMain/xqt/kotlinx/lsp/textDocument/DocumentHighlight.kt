// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

/**
 * A document highlight kind.
 *
 * @param kind the document highlight kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class DocumentHighlightKind(val kind: Int) {
    companion object : JsonSerialization<DocumentHighlightKind> {
        override fun serializeToJson(value: DocumentHighlightKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): DocumentHighlightKind {
            return DocumentHighlightKind(JsonInt.deserialize(json))
        }

        /**
         * A textual occurrence.
         */
        val Text: DocumentHighlightKind = DocumentHighlightKind(1)

        /**
         * Read-access of a symbol, like reading a variable.
         */
        val Read: DocumentHighlightKind = DocumentHighlightKind(2)

        /**
         * Write-access of a symbol, like writing to a variable.
         */
        val Write: DocumentHighlightKind = DocumentHighlightKind(3)
    }
}
