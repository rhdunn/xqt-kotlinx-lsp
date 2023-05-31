// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.DocumentFilter
import xqt.kotlinx.lsp.types.Language
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.uri.UriScheme
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The DocumentFilter type")
class TheDocumentFilterType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf()

        val filter = DocumentFilter.deserialize(json)
        assertEquals(null, filter.language)
        assertEquals(null, filter.scheme)
        assertEquals(null, filter.pattern)

        assertEquals(json, DocumentFilter.serializeToJson(filter))
    }

    @Test
    @DisplayName("supports the language property")
    fun supports_the_language_property() {
        val json = jsonObjectOf(
            "language" to JsonPrimitive("typescript")
        )

        val filter = DocumentFilter.deserialize(json)
        assertEquals(Language("typescript"), filter.language)
        assertEquals(null, filter.scheme)
        assertEquals(null, filter.pattern)

        assertEquals(json, DocumentFilter.serializeToJson(filter))
    }

    @Test
    @DisplayName("supports the scheme property")
    fun supports_the_scheme_property() {
        val json = jsonObjectOf(
            "scheme" to JsonPrimitive("file")
        )

        val filter = DocumentFilter.deserialize(json)
        assertEquals(null, filter.language)
        assertEquals(UriScheme.File, filter.scheme)
        assertEquals(null, filter.pattern)

        assertEquals(json, DocumentFilter.serializeToJson(filter))
    }

    @Test
    @DisplayName("supports the pattern property")
    fun supports_the_pattern_property() {
        val json = jsonObjectOf(
            "pattern" to JsonPrimitive("*.{ts,js}")
        )

        val filter = DocumentFilter.deserialize(json)
        assertEquals(null, filter.language)
        assertEquals(null, filter.scheme)
        assertEquals("*.{ts,js}", filter.pattern)

        assertEquals(json, DocumentFilter.serializeToJson(filter))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DocumentFilter.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { DocumentFilter.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { DocumentFilter.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { DocumentFilter.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DocumentFilter.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DocumentFilter.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
