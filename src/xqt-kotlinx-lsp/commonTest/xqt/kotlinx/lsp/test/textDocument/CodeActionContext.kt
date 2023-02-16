// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CodeActionContext
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The CodeActionContext type")
class TheCodeActionContextType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "diagnostics" to jsonArrayOf()
        )

        val ctx = CodeActionContext.deserialize(json)
        assertEquals(0, ctx.diagnostics.size)

        assertEquals(json, CodeActionContext.serializeToJson(ctx))
    }

    @Test
    @DisplayName("supports the diagnostics property")
    fun supports_the_resolve_provider_property() {
        val json = jsonObjectOf(
            "diagnostics" to jsonArrayOf(
                jsonObjectOf(
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
                    "message" to JsonPrimitive("Lorem ipsum dolor.")
                )
            )
        )

        val ctx = CodeActionContext.deserialize(json)
        assertEquals(1, ctx.diagnostics.size)

        assertEquals(Position(5u, 12u), ctx.diagnostics[0].range.start)
        assertEquals(Position(5u, 21u), ctx.diagnostics[0].range.end)
        assertEquals(null, ctx.diagnostics[0].severity)
        assertEquals(null, ctx.diagnostics[0].code)
        assertEquals(null, ctx.diagnostics[0].source)
        assertEquals("Lorem ipsum dolor.", ctx.diagnostics[0].message)

        assertEquals(json, CodeActionContext.serializeToJson(ctx))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { CodeActionContext.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { CodeActionContext.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { CodeActionContext.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { CodeActionContext.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { CodeActionContext.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { CodeActionContext.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
