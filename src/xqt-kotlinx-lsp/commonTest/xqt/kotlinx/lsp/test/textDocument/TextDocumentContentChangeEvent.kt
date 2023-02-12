// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.TextDocumentContentChangeEvent
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The TextDocumentContentChangeEvent type")
class TheTextDocumentContentChangeEventType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "text" to JsonPrimitive("Lorem Ipsum")
        )

        val params = TextDocumentContentChangeEvent.deserialize(json)
        assertEquals(null, params.range)
        assertEquals(null, params.rangeLength)
        assertEquals("Lorem Ipsum", params.text)

        assertEquals(json, TextDocumentContentChangeEvent.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the range property")
    fun supports_the_range_property() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(23)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(6),
                    "character" to JsonPrimitive(0)
                )
            ),
            "text" to JsonPrimitive("Lorem Ipsum")
        )

        val params = TextDocumentContentChangeEvent.deserialize(json)
        assertEquals(null, params.rangeLength)
        assertEquals("Lorem Ipsum", params.text)

        assertEquals(Position(5u, 23u), params.range?.start)
        assertEquals(Position(6u, 0u), params.range?.end)

        assertEquals(json, TextDocumentContentChangeEvent.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the range length property")
    fun supports_the_range_length_property() {
        val json = jsonObjectOf(
            "rangeLength" to JsonPrimitive(18),
            "text" to JsonPrimitive("Lorem Ipsum")
        )

        val params = TextDocumentContentChangeEvent.deserialize(json)
        assertEquals(null, params.range)
        assertEquals(18u, params.rangeLength)
        assertEquals("Lorem Ipsum", params.text)

        assertEquals(json, TextDocumentContentChangeEvent.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { TextDocumentContentChangeEvent.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { TextDocumentContentChangeEvent.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { TextDocumentContentChangeEvent.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { TextDocumentContentChangeEvent.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { TextDocumentContentChangeEvent.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { TextDocumentContentChangeEvent.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
