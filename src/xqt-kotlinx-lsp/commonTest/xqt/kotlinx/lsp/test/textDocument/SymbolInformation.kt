// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.SymbolInformation
import xqt.kotlinx.lsp.textDocument.SymbolKind
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The SymbolInformation type")
class TheSymbolInformationType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "name" to JsonPrimitive("Lorem Ipsum"),
            "kind" to JsonPrimitive(7),
            "location" to jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
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
        )

        val si = SymbolInformation.deserialize(json)
        assertEquals("Lorem Ipsum", si.name)
        assertEquals(SymbolKind.Property, si.kind)
        assertEquals(DocumentUri("file:///home/lorem/ipsum.py"), si.location.uri)
        assertEquals(Position(5u, 12u), si.location.range.start)
        assertEquals(Position(5u, 21u), si.location.range.end)

        assertEquals(json, SymbolInformation.serializeToJson(si))
    }

    @Test
    @DisplayName("supports the containerName property")
    fun supports_the_container_name_property() {
        val json = jsonObjectOf(
            "name" to JsonPrimitive("Lorem Ipsum"),
            "kind" to JsonPrimitive(7),
            "location" to jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
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
        )

        val si = SymbolInformation.deserialize(json)
        assertEquals("Lorem Ipsum", si.name)
        assertEquals(SymbolKind.Property, si.kind)
        assertEquals(DocumentUri("file:///home/lorem/ipsum.py"), si.location.uri)
        assertEquals(Position(5u, 12u), si.location.range.start)
        assertEquals(Position(5u, 21u), si.location.range.end)

        assertEquals(json, SymbolInformation.serializeToJson(si))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { SymbolInformation.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { SymbolInformation.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { SymbolInformation.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { SymbolInformation.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { SymbolInformation.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { SymbolInformation.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
