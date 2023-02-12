// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CompletionItemKind
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The CompletionItemKind type")
class TheCompletionItemKindType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("1", CompletionItemKind.serializeToJson(CompletionItemKind.Text).toString())
        assertEquals("2", CompletionItemKind.serializeToJson(CompletionItemKind.Method).toString())
        assertEquals("3", CompletionItemKind.serializeToJson(CompletionItemKind.Function).toString())
        assertEquals("4", CompletionItemKind.serializeToJson(CompletionItemKind.Constructor).toString())
        assertEquals("5", CompletionItemKind.serializeToJson(CompletionItemKind.Field).toString())
        assertEquals("6", CompletionItemKind.serializeToJson(CompletionItemKind.Variable).toString())
        assertEquals("7", CompletionItemKind.serializeToJson(CompletionItemKind.Class).toString())
        assertEquals("8", CompletionItemKind.serializeToJson(CompletionItemKind.Interface).toString())
        assertEquals("9", CompletionItemKind.serializeToJson(CompletionItemKind.Module).toString())
        assertEquals("10", CompletionItemKind.serializeToJson(CompletionItemKind.Property).toString())
        assertEquals("11", CompletionItemKind.serializeToJson(CompletionItemKind.Unit).toString())
        assertEquals("12", CompletionItemKind.serializeToJson(CompletionItemKind.Value).toString())
        assertEquals("13", CompletionItemKind.serializeToJson(CompletionItemKind.Enum).toString())
        assertEquals("14", CompletionItemKind.serializeToJson(CompletionItemKind.Keyword).toString())
        assertEquals("15", CompletionItemKind.serializeToJson(CompletionItemKind.Snippet).toString())
        assertEquals("16", CompletionItemKind.serializeToJson(CompletionItemKind.Color).toString())
        assertEquals("17", CompletionItemKind.serializeToJson(CompletionItemKind.File).toString())
        assertEquals("18", CompletionItemKind.serializeToJson(CompletionItemKind.Reference).toString())

        assertEquals("900", CompletionItemKind.serializeToJson(CompletionItemKind(900)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(CompletionItemKind.Text, CompletionItemKind.deserialize(JsonPrimitive(1)))
        assertEquals(CompletionItemKind.Method, CompletionItemKind.deserialize(JsonPrimitive(2)))
        assertEquals(CompletionItemKind.Function, CompletionItemKind.deserialize(JsonPrimitive(3)))
        assertEquals(CompletionItemKind.Constructor, CompletionItemKind.deserialize(JsonPrimitive(4)))
        assertEquals(CompletionItemKind.Field, CompletionItemKind.deserialize(JsonPrimitive(5)))
        assertEquals(CompletionItemKind.Variable, CompletionItemKind.deserialize(JsonPrimitive(6)))
        assertEquals(CompletionItemKind.Class, CompletionItemKind.deserialize(JsonPrimitive(7)))
        assertEquals(CompletionItemKind.Interface, CompletionItemKind.deserialize(JsonPrimitive(8)))
        assertEquals(CompletionItemKind.Module, CompletionItemKind.deserialize(JsonPrimitive(9)))
        assertEquals(CompletionItemKind.Property, CompletionItemKind.deserialize(JsonPrimitive(10)))
        assertEquals(CompletionItemKind.Unit, CompletionItemKind.deserialize(JsonPrimitive(11)))
        assertEquals(CompletionItemKind.Value, CompletionItemKind.deserialize(JsonPrimitive(12)))
        assertEquals(CompletionItemKind.Enum, CompletionItemKind.deserialize(JsonPrimitive(13)))
        assertEquals(CompletionItemKind.Keyword, CompletionItemKind.deserialize(JsonPrimitive(14)))
        assertEquals(CompletionItemKind.Snippet, CompletionItemKind.deserialize(JsonPrimitive(15)))
        assertEquals(CompletionItemKind.Color, CompletionItemKind.deserialize(JsonPrimitive(16)))
        assertEquals(CompletionItemKind.File, CompletionItemKind.deserialize(JsonPrimitive(17)))
        assertEquals(CompletionItemKind.Reference, CompletionItemKind.deserialize(JsonPrimitive(18)))

        assertEquals(CompletionItemKind(900), CompletionItemKind.deserialize(JsonPrimitive(900)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { CompletionItemKind.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { CompletionItemKind.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { CompletionItemKind.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { CompletionItemKind.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { CompletionItemKind.deserialize(JsonPrimitive("abc")) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'string'", e5.message)

        val e6 = assertFails { CompletionItemKind.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
