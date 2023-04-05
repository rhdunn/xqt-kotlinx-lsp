// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.GoTo
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The GoTo response result")
class TheGoToResponseResult {
    @Test
    @DisplayName("supports a location object")
    fun supports_a_location_object() {
        val json = jsonObjectOf(
            "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
            "range" to jsonObjectOf(
                "start" to jsonObjectOf(
                    "line" to JsonPrimitive(2),
                    "character" to JsonPrimitive(6)
                ),
                "end" to jsonObjectOf(
                    "line" to JsonPrimitive(2),
                    "character" to JsonPrimitive(8)
                )
            )
        )

        val goto = GoTo.deserialize(json)

        assertEquals(1, goto.size)
        assertEquals(1, goto.locations.size)

        assertEquals("file:///home/lorem/ipsum.py", goto.locations[0].uri)
        assertEquals(Position(2u, 6u), goto.locations[0].range.start)
        assertEquals(Position(2u, 8u), goto.locations[0].range.end)

        assertEquals(goto.locations[0], goto.location)

        assertEquals(json, GoTo.serializeToJson(goto))
    }

    @Test
    @DisplayName("supports an empty locations array")
    fun supports_an_empty_locations_array() {
        val json = jsonArrayOf()

        val goto = GoTo.deserialize(json)

        assertEquals(0, goto.size)
        assertEquals(0, goto.locations.size)

        assertEquals(null, goto.location)

        assertEquals(json, GoTo.serializeToJson(goto))
    }

    @Test
    @DisplayName("supports a locations array with a single location")
    fun supports_a_locations_array_with_a_single_location() {
        val json = jsonArrayOf(
            jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                "range" to jsonObjectOf(
                    "start" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    ),
                    "end" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(8)
                    )
                )
            )
        )

        val goto = GoTo.deserialize(json)

        assertEquals(1, goto.size)
        assertEquals(1, goto.locations.size)

        assertEquals("file:///home/lorem/ipsum.py", goto.locations[0].uri)
        assertEquals(Position(2u, 6u), goto.locations[0].range.start)
        assertEquals(Position(2u, 8u), goto.locations[0].range.end)

        assertEquals(goto.locations[0], goto.location)

        // NOTE: Persists a 1-element array as a Location object.
        assertEquals(json[0], GoTo.serializeToJson(goto))
    }

    @Test
    @DisplayName("supports a locations array with multiple locations")
    fun supports_a_locations_array_with_multiple_locations() {
        val json = jsonArrayOf(
            jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                "range" to jsonObjectOf(
                    "start" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(6)
                    ),
                    "end" to jsonObjectOf(
                        "line" to JsonPrimitive(2),
                        "character" to JsonPrimitive(8)
                    )
                )
            ),
            jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                "range" to jsonObjectOf(
                    "start" to jsonObjectOf(
                        "line" to JsonPrimitive(3),
                        "character" to JsonPrimitive(6)
                    ),
                    "end" to jsonObjectOf(
                        "line" to JsonPrimitive(3),
                        "character" to JsonPrimitive(8)
                    )
                )
            )
        )

        val goto = GoTo.deserialize(json)

        assertEquals(2, goto.size)
        assertEquals(2, goto.locations.size)

        assertEquals("file:///home/lorem/ipsum.py", goto.locations[0].uri)
        assertEquals(Position(2u, 6u), goto.locations[0].range.start)
        assertEquals(Position(2u, 8u), goto.locations[0].range.end)

        assertEquals("file:///home/lorem/ipsum.py", goto.locations[1].uri)
        assertEquals(Position(3u, 6u), goto.locations[1].range.start)
        assertEquals(Position(3u, 8u), goto.locations[1].range.end)

        assertEquals(goto.locations[0], goto.location)

        assertEquals(json, GoTo.serializeToJson(goto))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { GoTo.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'null'", e1.message)

        val e2 = assertFails { GoTo.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'string'", e2.message)

        val e3 = assertFails { GoTo.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'boolean'", e3.message)

        val e4 = assertFails { GoTo.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'integer'", e4.message)

        val e5 = assertFails { GoTo.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'decimal'", e5.message)
    }
}
