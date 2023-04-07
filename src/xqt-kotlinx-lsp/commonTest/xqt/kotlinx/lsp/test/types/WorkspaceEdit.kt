// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.WorkspaceEdit
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull

@DisplayName("The WorkspaceEdit type")
class TheWorkspaceEditType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "changes" to jsonObjectOf(
                "file:///home/lorem/lorem.py" to jsonArrayOf(),
                "file:///home/lorem/ipsum.py" to jsonArrayOf(
                    jsonObjectOf(
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
                        "newText" to JsonPrimitive("lorem")
                    ),
                    jsonObjectOf(
                        "range" to jsonObjectOf(
                            "start" to jsonObjectOf(
                                "line" to JsonPrimitive(5),
                                "character" to JsonPrimitive(15)
                            ),
                            "end" to jsonObjectOf(
                                "line" to JsonPrimitive(5),
                                "character" to JsonPrimitive(23)
                            )
                        ),
                        "newText" to JsonPrimitive("lorem")
                    )
                )
            )
        )

        val we = WorkspaceEdit.deserialize(json)
        assertEquals(2, we.changes.size)

        val lorem = we.changes["file:///home/lorem/lorem.py"]
        assertNotNull(lorem)
        assertEquals(0, lorem.size)

        val ipsum = we.changes["file:///home/lorem/ipsum.py"]
        assertNotNull(ipsum)
        assertEquals(2, ipsum.size)

        assertEquals(Position(5u, 23u), ipsum[0].range.start)
        assertEquals(Position(5u, 23u), ipsum[0].range.end)
        assertEquals("lorem", ipsum[0].newText)

        assertEquals(Position(5u, 15u), ipsum[1].range.start)
        assertEquals(Position(5u, 23u), ipsum[1].range.end)
        assertEquals("lorem", ipsum[1].newText)

        assertEquals(json, WorkspaceEdit.serializeToJson(we))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { WorkspaceEdit.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { WorkspaceEdit.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { WorkspaceEdit.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { WorkspaceEdit.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { WorkspaceEdit.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { WorkspaceEdit.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
