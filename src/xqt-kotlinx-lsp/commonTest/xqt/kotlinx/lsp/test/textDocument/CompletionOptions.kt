// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CompletionOptions
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The completion options")
class TheCompletionOptions {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf()

        val params = CompletionOptions.deserialize(json)
        assertEquals(null, params.resolveProvider)
        assertEquals(0, params.triggerCharacters.size)

        assertEquals(json, CompletionOptions.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the resolve provider property")
    fun supports_the_resolve_provider_property() {
        val json = jsonObjectOf(
            "resolveProvider" to JsonPrimitive(true)
        )

        val params = CompletionOptions.deserialize(json)
        assertEquals(true, params.resolveProvider)
        assertEquals(0, params.triggerCharacters.size)

        assertEquals(json, CompletionOptions.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the trigger characters property")
    fun supports_the_trigger_characters_property() {
        val json = jsonObjectOf(
            "triggerCharacters" to jsonArrayOf(
                JsonPrimitive("?"),
                JsonPrimitive(":")
            )
        )

        val params = CompletionOptions.deserialize(json)
        assertEquals(null, params.resolveProvider)
        assertEquals(2, params.triggerCharacters.size)

        assertEquals("?", params.triggerCharacters[0])
        assertEquals(":", params.triggerCharacters[1])

        assertEquals(json, CompletionOptions.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { CompletionOptions.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { CompletionOptions.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { CompletionOptions.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { CompletionOptions.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { CompletionOptions.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { CompletionOptions.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
