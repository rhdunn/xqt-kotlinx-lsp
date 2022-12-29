// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.DiagnosticSeverity
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The DiagnosticSeverity type")
class TheDiagnosticSeverityType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("1", DiagnosticSeverity.serializeToJson(DiagnosticSeverity.Error).toString())
        assertEquals("2", DiagnosticSeverity.serializeToJson(DiagnosticSeverity.Warning).toString())
        assertEquals("3", DiagnosticSeverity.serializeToJson(DiagnosticSeverity.Information).toString())
        assertEquals("4", DiagnosticSeverity.serializeToJson(DiagnosticSeverity.Hint).toString())

        assertEquals("900", DiagnosticSeverity.serializeToJson(DiagnosticSeverity(900)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(DiagnosticSeverity.Error, DiagnosticSeverity.deserialize(JsonPrimitive(1)))
        assertEquals(DiagnosticSeverity.Warning, DiagnosticSeverity.deserialize(JsonPrimitive(2)))
        assertEquals(DiagnosticSeverity.Information, DiagnosticSeverity.deserialize(JsonPrimitive(3)))
        assertEquals(DiagnosticSeverity.Hint, DiagnosticSeverity.deserialize(JsonPrimitive(4)))

        assertEquals(DiagnosticSeverity(900), DiagnosticSeverity.deserialize(JsonPrimitive(900)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DiagnosticSeverity.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { DiagnosticSeverity.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { DiagnosticSeverity.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { DiagnosticSeverity.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DiagnosticSeverity.deserialize(JsonPrimitive("abc")) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'string'", e5.message)

        val e6 = assertFails { DiagnosticSeverity.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
