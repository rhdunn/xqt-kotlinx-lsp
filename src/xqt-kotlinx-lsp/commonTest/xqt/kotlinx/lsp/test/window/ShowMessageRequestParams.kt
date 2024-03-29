// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.window

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.window.MessageType
import xqt.kotlinx.lsp.window.ShowMessageRequestParams
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The window/showMessageRequest request parameters")
class TheWindowShowMessageRequestParameters {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "type" to JsonPrimitive(2),
            "message" to JsonPrimitive("Lorem Ipsum")
        )

        val params = ShowMessageRequestParams.deserialize(json)
        assertEquals(MessageType.Warning, params.type)
        assertEquals("Lorem Ipsum", params.message)
        assertEquals(0, params.actions.size)

        assertEquals(json, ShowMessageRequestParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the actions property")
    fun supports_the_actions_property() {
        val json = jsonObjectOf(
            "type" to JsonPrimitive(2),
            "message" to JsonPrimitive("Lorem Ipsum"),
            "actions" to jsonArrayOf(
                jsonObjectOf(
                    "title" to JsonPrimitive("Yes")
                ),
                jsonObjectOf(
                    "title" to JsonPrimitive("No")
                )
            )
        )

        val params = ShowMessageRequestParams.deserialize(json)
        assertEquals(MessageType.Warning, params.type)
        assertEquals("Lorem Ipsum", params.message)

        assertEquals(2, params.actions.size)
        assertEquals("Yes", params.actions[0].title)
        assertEquals("No", params.actions[1].title)

        assertEquals(json, ShowMessageRequestParams.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { ShowMessageRequestParams.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { ShowMessageRequestParams.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { ShowMessageRequestParams.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { ShowMessageRequestParams.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { ShowMessageRequestParams.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { ShowMessageRequestParams.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
