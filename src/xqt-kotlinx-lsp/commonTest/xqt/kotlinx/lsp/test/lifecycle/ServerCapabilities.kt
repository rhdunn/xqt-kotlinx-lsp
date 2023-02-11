// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.lifecycle.ServerCapabilities
import xqt.kotlinx.lsp.textDocument.TextDocumentSyncKind
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The server capabilities")
class TheServerCapabilities {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf()

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the text document sync property")
    fun supports_the_text_document_sync_property() {
        val json = jsonObjectOf(
            "textDocumentSync" to JsonPrimitive(1)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(TextDocumentSyncKind.Full, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the hover provider property")
    fun supports_the_hover_provider_property() {
        val json = jsonObjectOf(
            "hoverProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(true, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the completion provider property")
    fun supports_the_completion_provider_property() {
        val json = jsonObjectOf(
            "completionProvider" to jsonObjectOf(
                "resolveProvider" to JsonPrimitive(true)
            )
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(true, params.completionProvider?.resolveProvider)
        assertEquals(0, params.completionProvider?.triggerCharacters?.size)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the signature help provider property")
    fun supports_the_signature_help_provider_property() {
        val json = jsonObjectOf(
            "signatureHelpProvider" to jsonObjectOf(
                "triggerCharacters" to jsonArrayOf(
                    JsonPrimitive("?"),
                    JsonPrimitive(":")
                )
            )
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(2, params.signatureHelpProvider?.triggerCharacters?.size)

        assertEquals("?", params.signatureHelpProvider?.triggerCharacters?.get(0))
        assertEquals(":", params.signatureHelpProvider?.triggerCharacters?.get(1))

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the references provider property")
    fun supports_the_references_provider_property() {
        val json = jsonObjectOf(
            "referencesProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(true, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the document highlight provider property")
    fun supports_the_document_highlight_provider_property() {
        val json = jsonObjectOf(
            "documentHighlightProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(true, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the document symbol provider property")
    fun supports_the_document_symbol_provider_property() {
        val json = jsonObjectOf(
            "documentSymbolProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(true, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the workspace symbol provider property")
    fun supports_the_workspace_symbol_provider_property() {
        val json = jsonObjectOf(
            "workspaceSymbolProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(true, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the code action provider property")
    fun supports_the_code_action_provider_property() {
        val json = jsonObjectOf(
            "codeActionProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(true, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the code lens provider property")
    fun supports_the_code_lens_provider_property() {
        val json = jsonObjectOf(
            "codeLensProvider" to jsonObjectOf(
                "resolveProvider" to JsonPrimitive(true)
            )
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(true, params.codeLensProvider?.resolveProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the document formatting provider property")
    fun supports_the_document_formatting_provider_property() {
        val json = jsonObjectOf(
            "documentFormattingProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(true, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the document range formatting provider property")
    fun supports_the_document_range_formatting_provider_property() {
        val json = jsonObjectOf(
            "documentRangeFormattingProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(true, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the document on type formatting provider property")
    fun supports_the_document_on_type_formatting_provider_property() {
        val json = jsonObjectOf(
            "documentOnTypeFormattingProvider" to jsonObjectOf(
                "firstTriggerCharacter" to JsonPrimitive("}")
            )
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.renameProvider)

        assertEquals("}", params.documentOnTypeFormattingProvider?.firstTriggerCharacter)
        assertEquals(0, params.documentOnTypeFormattingProvider?.moreTriggerCharacters?.size)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the rename provider property")
    fun supports_the_rename_provider_property() {
        val json = jsonObjectOf(
            "renameProvider" to JsonPrimitive(true)
        )

        val params = ServerCapabilities.deserialize(json)
        assertEquals(null, params.textDocumentSync)
        assertEquals(null, params.hoverProvider)
        assertEquals(null, params.completionProvider)
        assertEquals(null, params.signatureHelpProvider)
        assertEquals(null, params.referencesProvider)
        assertEquals(null, params.documentHighlightProvider)
        assertEquals(null, params.documentSymbolProvider)
        assertEquals(null, params.workspaceSymbolProvider)
        assertEquals(null, params.codeActionProvider)
        assertEquals(null, params.codeLensProvider)
        assertEquals(null, params.documentFormattingProvider)
        assertEquals(null, params.documentRangeFormattingProvider)
        assertEquals(null, params.documentOnTypeFormattingProvider)
        assertEquals(true, params.renameProvider)

        assertEquals(json, ServerCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { ServerCapabilities.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { ServerCapabilities.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { ServerCapabilities.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { ServerCapabilities.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { ServerCapabilities.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { ServerCapabilities.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
