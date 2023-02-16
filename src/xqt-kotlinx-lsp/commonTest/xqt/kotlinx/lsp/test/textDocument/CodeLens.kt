// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CodeLens
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The CodeLens type")
class TheCodeLensType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(12)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(21)
                )
            )
        )

        val lens = CodeLens.deserialize(json)
        assertEquals(Position(5u, 12u), lens.range.start)
        assertEquals(Position(5u, 21u), lens.range.end)
        assertEquals(null, lens.command)
        assertEquals(null, lens.data)

        assertEquals(json, CodeLens.serializeToJson(lens))
    }

    @Test
    @DisplayName("supports the command property")
    fun supports_the_command_property() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(12)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(21)
                )
            ),
            "command" to jsonObjectOf(
                "title" to JsonPrimitive("save"),
                "command" to JsonPrimitive("file.save")
            )
        )

        val lens = CodeLens.deserialize(json)
        assertEquals(Position(5u, 12u), lens.range.start)
        assertEquals(Position(5u, 21u), lens.range.end)
        assertEquals(null, lens.data)

        assertEquals("save", lens.command?.title)
        assertEquals("file.save", lens.command?.command)
        assertEquals(null, lens.command?.arguments)

        assertEquals(json, CodeLens.serializeToJson(lens))
    }

    @Test
    @DisplayName("supports the data property")
    fun supports_the_data_property() {
        val json = jsonObjectOf(
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(12)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(5),
                    "character" to JsonPrimitive(21)
                )
            ),
            "data" to JsonPrimitive("Lorem Ipsum")
        )

        val lens = CodeLens.deserialize(json)
        assertEquals(Position(5u, 12u), lens.range.start)
        assertEquals(Position(5u, 21u), lens.range.end)
        assertEquals(null, lens.command)
        assertEquals(JsonPrimitive("Lorem Ipsum"), lens.data)

        assertEquals(json, CodeLens.serializeToJson(lens))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { CodeLens.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { CodeLens.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { CodeLens.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { CodeLens.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { CodeLens.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { CodeLens.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
