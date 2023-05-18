// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.CompletionItem
import xqt.kotlinx.lsp.textDocument.CompletionItemKind
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The CompletionItem type")
class TheCompletionItemType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the kind property")
    fun supports_the_kind_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "kind" to JsonPrimitive(10)
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(CompletionItemKind.Property, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the detail property")
    fun supports_the_detail_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "detail" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals("Lorem ipsum dolor", params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the documentation property")
    fun supports_the_documentation_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "documentation" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals("Lorem ipsum dolor", params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the sortText property")
    fun supports_the_sort_text_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "sortText" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals("Lorem ipsum dolor", params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the filterText property")
    fun supports_the_filterText_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "filterText" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals("Lorem ipsum dolor", params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the insertText property")
    fun supports_the_insertText_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "insertText" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals("Lorem ipsum dolor", params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(null, params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the textEdit property")
    fun supports_the_text_edit_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "textEdit" to jsonObjectOf(
                "range" to jsonObjectOf(
                    "start" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(23)
                    ),
                    "end" to jsonObjectOf(
                        "line" to JsonPrimitive(5),
                        "character" to JsonPrimitive(23)
                    )
                ),
                "newText" to JsonPrimitive("Lorem ipsum dolor")
            )
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.data)
        assertEquals(0, params.additionalTextEdits.size)

        assertEquals(Position(5u, 23u), params.textEdit?.range?.start)
        assertEquals(Position(5u, 23u), params.textEdit?.range?.end)
        assertEquals("Lorem ipsum dolor", params.textEdit?.newText)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the additionalTextEdits property")
    fun supports_the_additional_text_edits_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "additionalTextEdits" to jsonArrayOf(
                jsonObjectOf(
                    "range" to jsonObjectOf(
                        "start" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(23)
                        ),
                        "end" to jsonObjectOf(
                            "line" to JsonPrimitive(5),
                            "character" to JsonPrimitive(23)
                        )
                    ),
                    "newText" to JsonPrimitive("Lorem ipsum dolor")
                )
            )
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(null, params.data)

        val textEdit = params.additionalTextEdits[0]
        assertEquals(Position(5u, 23u), textEdit.range.start)
        assertEquals(Position(5u, 23u), textEdit.range.end)
        assertEquals("Lorem ipsum dolor", textEdit.newText)

        assertEquals(json, CompletionItem.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the data property")
    fun supports_the_data_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem Ipsum"),
            "data" to JsonPrimitive("Lorem ipsum dolor")
        )

        val params = CompletionItem.deserialize(json)
        assertEquals("Lorem Ipsum", params.label)
        assertEquals(null, params.kind)
        assertEquals(null, params.detail)
        assertEquals(null, params.documentation)
        assertEquals(null, params.sortText)
        assertEquals(null, params.filterText)
        assertEquals(null, params.insertText)
        assertEquals(null, params.textEdit)
        assertEquals(0, params.additionalTextEdits.size)
        assertEquals(JsonPrimitive("Lorem ipsum dolor"), params.data)

        assertEquals(json, CompletionItem.serializeToJson(params))
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

        val e3 = assertFails { CompletionItem.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { CompletionItem.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { CompletionItem.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { CompletionItem.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
