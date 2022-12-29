// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.*
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The Diagnostic type")
class TheDiagnosticType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "message" to JsonPrimitive("Lorem ipsum dolor.")
        )

        val diagnostic = Diagnostic.deserialize(json)
        assertEquals(Position(5u, 23u), diagnostic.range.start)
        assertEquals(Position(5u, 23u), diagnostic.range.end)
        assertEquals(null, diagnostic.severity)
        assertEquals(null, diagnostic.code)
        assertEquals(null, diagnostic.source)
        assertEquals("Lorem ipsum dolor.", diagnostic.message)

        assertEquals(json, Diagnostic.serializeToJson(diagnostic))
    }

    @Test
    @DisplayName("supports the severity property")
    fun supports_the_severity_property() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "severity" to JsonPrimitive(1),
            "message" to JsonPrimitive("Lorem ipsum dolor.")
        )

        val diagnostic = Diagnostic.deserialize(json)
        assertEquals(Position(5u, 23u), diagnostic.range.start)
        assertEquals(Position(5u, 23u), diagnostic.range.end)
        assertEquals(DiagnosticSeverity.Error, diagnostic.severity)
        assertEquals(null, diagnostic.code)
        assertEquals(null, diagnostic.source)
        assertEquals("Lorem ipsum dolor.", diagnostic.message)

        assertEquals(json, Diagnostic.serializeToJson(diagnostic))
    }

    @Test
    @DisplayName("supports the code property as an integer")
    fun supports_the_code_property_as_an_integer() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "code" to JsonPrimitive(1234),
            "message" to JsonPrimitive("Lorem ipsum dolor.")
        )

        val diagnostic = Diagnostic.deserialize(json)
        assertEquals(Position(5u, 23u), diagnostic.range.start)
        assertEquals(Position(5u, 23u), diagnostic.range.end)
        assertEquals(null, diagnostic.severity)
        assertEquals(JsonIntOrString.IntegerValue(1234), diagnostic.code)
        assertEquals(null, diagnostic.source)
        assertEquals("Lorem ipsum dolor.", diagnostic.message)

        assertEquals(json, Diagnostic.serializeToJson(diagnostic))
    }

    @Test
    @DisplayName("supports the code property as a string")
    fun supports_the_code_property_as_a_string() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "code" to JsonPrimitive("abc"),
            "message" to JsonPrimitive("Lorem ipsum dolor.")
        )

        val diagnostic = Diagnostic.deserialize(json)
        assertEquals(Position(5u, 23u), diagnostic.range.start)
        assertEquals(Position(5u, 23u), diagnostic.range.end)
        assertEquals(null, diagnostic.severity)
        assertEquals(JsonIntOrString.StringValue("abc"), diagnostic.code)
        assertEquals(null, diagnostic.source)
        assertEquals("Lorem ipsum dolor.", diagnostic.message)

        assertEquals(json, Diagnostic.serializeToJson(diagnostic))
    }

    @Test
    @DisplayName("supports the source property")
    fun supports_the_source_property() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                )
            ),
            "source" to JsonPrimitive("test case"),
            "message" to JsonPrimitive("Lorem ipsum dolor.")
        )

        val diagnostic = Diagnostic.deserialize(json)
        assertEquals(Position(5u, 23u), diagnostic.range.start)
        assertEquals(Position(5u, 23u), diagnostic.range.end)
        assertEquals(null, diagnostic.severity)
        assertEquals(null, diagnostic.code)
        assertEquals("test case", diagnostic.source)
        assertEquals("Lorem ipsum dolor.", diagnostic.message)

        assertEquals(json, Diagnostic.serializeToJson(diagnostic))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { Diagnostic.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { Diagnostic.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { Diagnostic.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { Diagnostic.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { Diagnostic.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { Diagnostic.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
