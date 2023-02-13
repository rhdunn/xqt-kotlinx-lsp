// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.Hover
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The Hover type")
class TheHoverType {
    @Test
    @DisplayName("supports the contents property as a MarkedString string")
    fun supports_the_contents_property_as_a_marked_string_string() {
        val json = jsonObjectOf(
            "contents" to JsonPrimitive("Lorem ipsum dolor")
        )

        val hover = Hover.deserialize(json)
        assertEquals(1, hover.contents.size)
        assertEquals(null, hover.range)

        assertEquals(null, hover.contents[0].language)
        assertEquals("Lorem ipsum dolor", hover.contents[0].value)

        assertEquals(json, Hover.serializeToJson(hover))
    }

    @Test
    @DisplayName("supports the contents property as a MarkedString object")
    fun supports_the_contents_property_as_a_marked_string_object() {
        val json = jsonObjectOf(
            "contents" to jsonObjectOf(
                "language" to JsonPrimitive("plaintext"),
                "value" to JsonPrimitive("Lorem ipsum dolor")
            )
        )

        val hover = Hover.deserialize(json)
        assertEquals(1, hover.contents.size)
        assertEquals(null, hover.range)

        assertEquals("plaintext", hover.contents[0].language)
        assertEquals("Lorem ipsum dolor", hover.contents[0].value)

        assertEquals(json, Hover.serializeToJson(hover))
    }

    @Test
    @DisplayName("supports the contents property as a MarkedString array")
    fun supports_the_contents_property_as_a_marked_string_array() {
        val json = jsonObjectOf(
            "contents" to jsonArrayOf(
                JsonPrimitive("one"),
                jsonObjectOf(
                    "language" to JsonPrimitive("plaintext"),
                    "value" to JsonPrimitive("two")
                ),
                JsonPrimitive("three")
            )
        )

        val hover = Hover.deserialize(json)
        assertEquals(3, hover.contents.size)
        assertEquals(null, hover.range)

        assertEquals(null, hover.contents[0].language)
        assertEquals("one", hover.contents[0].value)

        assertEquals("plaintext", hover.contents[1].language)
        assertEquals("two", hover.contents[1].value)

        assertEquals(null, hover.contents[2].language)
        assertEquals("three", hover.contents[2].value)

        assertEquals(json, Hover.serializeToJson(hover))
    }

    @Test
    @DisplayName("supports the range property")
    fun supports_the_range_property() {
        val json = jsonObjectOf(
            "contents" to JsonPrimitive("Lorem ipsum dolor"),
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(6),
                    "character" to JsonPrimitive(0)
                )
            )
        )

        val hover = Hover.deserialize(json)
        assertEquals(1, hover.contents.size)

        assertEquals(Position(5u, 23u), hover.range?.start)
        assertEquals(Position(6u, 0u), hover.range?.end)

        assertEquals(null, hover.contents[0].language)
        assertEquals("Lorem ipsum dolor", hover.contents[0].value)

        assertEquals(json, Hover.serializeToJson(hover))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { Hover.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { Hover.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { Hover.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { Hover.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { Hover.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { Hover.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
