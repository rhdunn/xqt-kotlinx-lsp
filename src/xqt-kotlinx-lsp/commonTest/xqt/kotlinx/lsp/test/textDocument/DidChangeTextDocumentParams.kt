// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.DidChangeTextDocumentParams
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The textDocument/didChange notification parameters")
class TheTextDocumentDidChangeNotificationParameters {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "textDocument" to jsonObjectOf(
                "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                "version" to JsonPrimitive(12)
            ),
            "contentChanges" to jsonArrayOf(
                jsonObjectOf(
                    "text" to JsonPrimitive("Lorem Ipsum")
                )
            )
        )

        val params = DidChangeTextDocumentParams.deserialize(json)
        assertEquals(DocumentUri("file:///home/lorem/ipsum.py"), params.textDocument.uri)
        assertEquals(1, params.contentChanges.size)

        assertEquals(null, params.contentChanges[0].range)
        assertEquals(null, params.contentChanges[0].rangeLength)
        assertEquals("Lorem Ipsum", params.contentChanges[0].text)

        assertEquals(json, DidChangeTextDocumentParams.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DidChangeTextDocumentParams.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { DidChangeTextDocumentParams.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { DidChangeTextDocumentParams.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { DidChangeTextDocumentParams.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DidChangeTextDocumentParams.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DidChangeTextDocumentParams.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
