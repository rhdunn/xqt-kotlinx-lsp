// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.SignatureInformation
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The SignatureInformation type")
class TheSignatureInformationType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem"),
            "parameters" to jsonArrayOf()
        )

        val sig = SignatureInformation.deserialize(json)
        assertEquals("Lorem", sig.label)
        assertEquals(null, sig.documentation)
        assertEquals(0, sig.parameters.size)

        assertEquals(json, SignatureInformation.serializeToJson(sig))
    }

    @Test
    @DisplayName("supports the documentation property")
    fun supports_the_documentation_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem"),
            "documentation" to JsonPrimitive("Lorem ipsum dolor"),
            "parameters" to jsonArrayOf()
        )

        val sig = SignatureInformation.deserialize(json)
        assertEquals("Lorem", sig.label)
        assertEquals("Lorem ipsum dolor", sig.documentation)
        assertEquals(0, sig.parameters.size)

        assertEquals(json, SignatureInformation.serializeToJson(sig))
    }

    @Test
    @DisplayName("supports the parameters property")
    fun supports_the_parameters_property() {
        val json = jsonObjectOf(
            "label" to JsonPrimitive("Lorem"),
            "documentation" to JsonPrimitive("Lorem ipsum dolor"),
            "parameters" to jsonArrayOf(
                jsonObjectOf(
                    "label" to JsonPrimitive("Lorem")
                ),
                jsonObjectOf(
                    "label" to JsonPrimitive("Ipsum")
                )
            )
        )

        val sig = SignatureInformation.deserialize(json)
        assertEquals("Lorem", sig.label)
        assertEquals("Lorem ipsum dolor", sig.documentation)
        assertEquals(2, sig.parameters.size)

        assertEquals("Lorem", sig.parameters[0].label)
        assertEquals(null, sig.parameters[0].documentation)

        assertEquals("Ipsum", sig.parameters[1].label)
        assertEquals(null, sig.parameters[1].documentation)

        assertEquals(json, SignatureInformation.serializeToJson(sig))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { SignatureInformation.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { SignatureInformation.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { SignatureInformation.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { SignatureInformation.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { SignatureInformation.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { SignatureInformation.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
