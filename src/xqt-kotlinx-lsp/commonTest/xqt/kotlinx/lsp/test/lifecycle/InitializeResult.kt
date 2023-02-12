// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.lifecycle.InitializeResults
import xqt.kotlinx.lsp.textDocument.TextDocumentSyncKind
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The initialize result")
class TheInitializeResult {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "capabilities" to jsonObjectOf(
                "textDocumentSync" to JsonPrimitive(1)
            )
        )

        val params = InitializeResults.deserialize(json)
        assertEquals(TextDocumentSyncKind.Full, params.capabilities.textDocumentSync)
        assertEquals(null, params.capabilities.hoverProvider)
        assertEquals(null, params.capabilities.completionProvider)
        assertEquals(null, params.capabilities.signatureHelpProvider)
        assertEquals(null, params.capabilities.referencesProvider)
        assertEquals(null, params.capabilities.documentHighlightProvider)
        assertEquals(null, params.capabilities.documentSymbolProvider)
        assertEquals(null, params.capabilities.workspaceSymbolProvider)
        assertEquals(null, params.capabilities.codeActionProvider)
        assertEquals(null, params.capabilities.codeLensProvider)
        assertEquals(null, params.capabilities.documentFormattingProvider)
        assertEquals(null, params.capabilities.documentRangeFormattingProvider)
        assertEquals(null, params.capabilities.documentOnTypeFormattingProvider)
        assertEquals(null, params.capabilities.renameProvider)

        assertEquals(json, InitializeResults.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { InitializeResults.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { InitializeResults.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { InitializeResults.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { InitializeResults.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { InitializeResults.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { InitializeResults.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}