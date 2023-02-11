// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.DocumentOnTypeFormattingOptions
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The document on type formatting options")
class TheDocumentOnTypeFormattingOptions {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "firstTriggerCharacter" to JsonPrimitive("}")
        )

        val params = DocumentOnTypeFormattingOptions.deserialize(json)
        assertEquals("}", params.firstTriggerCharacter)
        assertEquals(0, params.moreTriggerCharacters.size)

        assertEquals(json, DocumentOnTypeFormattingOptions.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the more trigger characters property")
    fun supports_the_trigger_characters_property() {
        val json = jsonObjectOf(
            "firstTriggerCharacter" to JsonPrimitive("}"),
            // NOTE: The moreTriggerCharacters property is mistyped in the LSP specification.
            "moreTriggerCharacter" to jsonArrayOf(
                JsonPrimitive(")"),
                JsonPrimitive("]")
            )
        )

        val params = DocumentOnTypeFormattingOptions.deserialize(json)
        assertEquals("}", params.firstTriggerCharacter)
        assertEquals(2, params.moreTriggerCharacters.size)

        assertEquals(")", params.moreTriggerCharacters[0])
        assertEquals("]", params.moreTriggerCharacters[1])

        assertEquals(json, DocumentOnTypeFormattingOptions.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DocumentOnTypeFormattingOptions.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { DocumentOnTypeFormattingOptions.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { DocumentOnTypeFormattingOptions.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { DocumentOnTypeFormattingOptions.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DocumentOnTypeFormattingOptions.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DocumentOnTypeFormattingOptions.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
