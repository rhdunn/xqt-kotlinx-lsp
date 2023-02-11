// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.TextDocumentSyncKind
import xqt.kotlinx.lsp.types.Command
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The TextDocumentSyncKind type")
class TheTextDocumentSyncKindType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("0", TextDocumentSyncKind.serializeToJson(TextDocumentSyncKind.None).toString())
        assertEquals("1", TextDocumentSyncKind.serializeToJson(TextDocumentSyncKind.Full).toString())
        assertEquals("2", TextDocumentSyncKind.serializeToJson(TextDocumentSyncKind.Incremental).toString())

        assertEquals("909", TextDocumentSyncKind.serializeToJson(TextDocumentSyncKind(909)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(TextDocumentSyncKind.None, TextDocumentSyncKind.deserialize(JsonPrimitive(0)))
        assertEquals(TextDocumentSyncKind.Full, TextDocumentSyncKind.deserialize(JsonPrimitive(1)))
        assertEquals(TextDocumentSyncKind.Incremental, TextDocumentSyncKind.deserialize(JsonPrimitive(2)))

        assertEquals(TextDocumentSyncKind(909), TextDocumentSyncKind.deserialize(JsonPrimitive(909)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { TextDocumentSyncKind.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { TextDocumentSyncKind.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { TextDocumentSyncKind.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { Command.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'string'", e4.message)

        val e5 = assertFails { TextDocumentSyncKind.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'boolean'", e5.message)

        val e6 = assertFails { TextDocumentSyncKind.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
