// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.DocumentHighlightKind
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The DocumentHighlightKind type")
class TheDocumentHighlightKindType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("1", DocumentHighlightKind.serializeToJson(DocumentHighlightKind.Text).toString())
        assertEquals("2", DocumentHighlightKind.serializeToJson(DocumentHighlightKind.Read).toString())
        assertEquals("3", DocumentHighlightKind.serializeToJson(DocumentHighlightKind.Write).toString())

        assertEquals("900", DocumentHighlightKind.serializeToJson(DocumentHighlightKind(900)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(DocumentHighlightKind.Text, DocumentHighlightKind.deserialize(JsonPrimitive(1)))
        assertEquals(DocumentHighlightKind.Read, DocumentHighlightKind.deserialize(JsonPrimitive(2)))
        assertEquals(DocumentHighlightKind.Write, DocumentHighlightKind.deserialize(JsonPrimitive(3)))

        assertEquals(DocumentHighlightKind(900), DocumentHighlightKind.deserialize(JsonPrimitive(900)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DocumentHighlightKind.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { DocumentHighlightKind.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { DocumentHighlightKind.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { DocumentHighlightKind.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DocumentHighlightKind.deserialize(JsonPrimitive("abc")) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'string'", e5.message)

        val e6 = assertFails { DocumentHighlightKind.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
