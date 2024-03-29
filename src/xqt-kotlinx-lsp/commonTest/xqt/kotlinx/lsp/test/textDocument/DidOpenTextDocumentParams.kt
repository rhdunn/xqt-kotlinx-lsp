// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.DidOpenTextDocumentParams
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The textDocument/didOpen notification parameters")
class TheTextDocumentDidOpenNotificationParameters {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "textDocument" to jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                "languageId" to JsonPrimitive("python"),
                "version" to JsonPrimitive(12),
                "text" to JsonPrimitive("Lorem ipsum dolor.")
            )
        )

        val params = DidOpenTextDocumentParams.deserialize(json)
        assertEquals(DocumentUri("file:///home/lorem/ipsum.py"), params.textDocument.uri)
        assertEquals("python", params.textDocument.languageId)
        assertEquals(12, params.textDocument.version)
        assertEquals("Lorem ipsum dolor.", params.textDocument.text)

        assertEquals(json, DidOpenTextDocumentParams.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DidOpenTextDocumentParams.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { DidOpenTextDocumentParams.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { DidOpenTextDocumentParams.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { DidOpenTextDocumentParams.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DidOpenTextDocumentParams.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DidOpenTextDocumentParams.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
