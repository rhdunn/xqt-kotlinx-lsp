// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.lsp.textDocument.MarkedString
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The MarkedString type")
class TheMarkedStringType {
    @Test
    @DisplayName("supports JSON string values")
    fun supports_the_json_string_values() {
        val json = JsonPrimitive("Lorem ipsum dolor")

        val params = MarkedString.deserialize(json)
        assertEquals(null, params.language)
        assertEquals("Lorem ipsum dolor", params.value)

        assertEquals(json, MarkedString.serializeToJson(params))
    }

    @Test
    @DisplayName("supports JSON object values")
    fun supports_the_json_object_values() {
        val json = jsonObjectOf(
            "language" to JsonPrimitive("plaintext"),
            "value" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = MarkedString.deserialize(json)
        assertEquals("plaintext", params.language)
        assertEquals("Lorem ipsum dolor", params.value)

        assertEquals(json, MarkedString.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { CompletionItem.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { CompletionItem.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { CompletionItem.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'boolean'", e3.message)

        val e4 = assertFails { CompletionItem.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'integer'", e4.message)

        val e5 = assertFails { CompletionItem.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'decimal'", e5.message)
    }
}
