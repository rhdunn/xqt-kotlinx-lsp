// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.SymbolKind
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The SymbolKind type")
class TheSymbolKindType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("1", SymbolKind.serializeToJson(SymbolKind.File).toString())
        assertEquals("2", SymbolKind.serializeToJson(SymbolKind.Module).toString())
        assertEquals("3", SymbolKind.serializeToJson(SymbolKind.Namespace).toString())
        assertEquals("4", SymbolKind.serializeToJson(SymbolKind.Package).toString())
        assertEquals("5", SymbolKind.serializeToJson(SymbolKind.Class).toString())
        assertEquals("6", SymbolKind.serializeToJson(SymbolKind.Method).toString())
        assertEquals("7", SymbolKind.serializeToJson(SymbolKind.Property).toString())
        assertEquals("8", SymbolKind.serializeToJson(SymbolKind.Field).toString())
        assertEquals("9", SymbolKind.serializeToJson(SymbolKind.Constructor).toString())
        assertEquals("10", SymbolKind.serializeToJson(SymbolKind.Enum).toString())
        assertEquals("11", SymbolKind.serializeToJson(SymbolKind.Interface).toString())
        assertEquals("12", SymbolKind.serializeToJson(SymbolKind.Function).toString())
        assertEquals("13", SymbolKind.serializeToJson(SymbolKind.Variable).toString())
        assertEquals("14", SymbolKind.serializeToJson(SymbolKind.Constant).toString())
        assertEquals("15", SymbolKind.serializeToJson(SymbolKind.String).toString())
        assertEquals("16", SymbolKind.serializeToJson(SymbolKind.Number).toString())
        assertEquals("17", SymbolKind.serializeToJson(SymbolKind.Boolean).toString())
        assertEquals("18", SymbolKind.serializeToJson(SymbolKind.Array).toString())

        assertEquals("900", SymbolKind.serializeToJson(SymbolKind(900)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(SymbolKind.File, SymbolKind.deserialize(JsonPrimitive(1)))
        assertEquals(SymbolKind.Module, SymbolKind.deserialize(JsonPrimitive(2)))
        assertEquals(SymbolKind.Namespace, SymbolKind.deserialize(JsonPrimitive(3)))
        assertEquals(SymbolKind.Package, SymbolKind.deserialize(JsonPrimitive(4)))
        assertEquals(SymbolKind.Class, SymbolKind.deserialize(JsonPrimitive(5)))
        assertEquals(SymbolKind.Method, SymbolKind.deserialize(JsonPrimitive(6)))
        assertEquals(SymbolKind.Property, SymbolKind.deserialize(JsonPrimitive(7)))
        assertEquals(SymbolKind.Field, SymbolKind.deserialize(JsonPrimitive(8)))
        assertEquals(SymbolKind.Constructor, SymbolKind.deserialize(JsonPrimitive(9)))
        assertEquals(SymbolKind.Enum, SymbolKind.deserialize(JsonPrimitive(10)))
        assertEquals(SymbolKind.Interface, SymbolKind.deserialize(JsonPrimitive(11)))
        assertEquals(SymbolKind.Function, SymbolKind.deserialize(JsonPrimitive(12)))
        assertEquals(SymbolKind.Variable, SymbolKind.deserialize(JsonPrimitive(13)))
        assertEquals(SymbolKind.Constant, SymbolKind.deserialize(JsonPrimitive(14)))
        assertEquals(SymbolKind.String, SymbolKind.deserialize(JsonPrimitive(15)))
        assertEquals(SymbolKind.Number, SymbolKind.deserialize(JsonPrimitive(16)))
        assertEquals(SymbolKind.Boolean, SymbolKind.deserialize(JsonPrimitive(17)))
        assertEquals(SymbolKind.Array, SymbolKind.deserialize(JsonPrimitive(18)))

        assertEquals(SymbolKind(900), SymbolKind.deserialize(JsonPrimitive(900)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { SymbolKind.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { SymbolKind.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { SymbolKind.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { SymbolKind.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { SymbolKind.deserialize(JsonPrimitive("abc")) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'string'", e5.message)

        val e6 = assertFails { SymbolKind.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
