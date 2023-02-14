// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.textDocument

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.textDocument.SignatureHelp
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The SignatureHelp type")
class TheSignatureHelpType {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "signatures" to jsonArrayOf()
        )

        val sig = SignatureHelp.deserialize(json)
        assertEquals(0, sig.signatures.size)
        assertEquals(null, sig.activeSignature)
        assertEquals(null, sig.activeParameter)

        assertEquals(json, SignatureHelp.serializeToJson(sig))
    }

    @Test
    @DisplayName("supports the signatures property")
    fun supports_the_signatures_property() {
        val json = jsonObjectOf(
            "signatures" to jsonArrayOf(
                jsonObjectOf(
                    "label" to JsonPrimitive("Lorem"),
                    "parameters" to jsonArrayOf()
                )
            )
        )

        val sig = SignatureHelp.deserialize(json)
        assertEquals(1, sig.signatures.size)
        assertEquals(null, sig.activeSignature)
        assertEquals(null, sig.activeParameter)

        assertEquals("Lorem", sig.signatures[0].label)
        assertEquals(null, sig.signatures[0].documentation)
        assertEquals(0, sig.signatures[0].parameters.size)

        assertEquals(json, SignatureHelp.serializeToJson(sig))
    }

    @Test
    @DisplayName("supports the activeSignature property")
    fun supports_the_active_signature_property() {
        val json = jsonObjectOf(
            "signatures" to jsonArrayOf(),
            "activeSignature" to JsonPrimitive(1)
        )

        val sig = SignatureHelp.deserialize(json)
        assertEquals(0, sig.signatures.size)
        assertEquals(1u, sig.activeSignature)
        assertEquals(null, sig.activeParameter)

        assertEquals(json, SignatureHelp.serializeToJson(sig))
    }

    @Test
    @DisplayName("supports the activeParameter property")
    fun supports_the_active_parameter_property() {
        val json = jsonObjectOf(
            "signatures" to jsonArrayOf(),
            "activeParameter" to JsonPrimitive(1)
        )

        val sig = SignatureHelp.deserialize(json)
        assertEquals(0, sig.signatures.size)
        assertEquals(null, sig.activeSignature)
        assertEquals(1u, sig.activeParameter)

        assertEquals(json, SignatureHelp.serializeToJson(sig))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { SignatureHelp.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { SignatureHelp.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { SignatureHelp.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { SignatureHelp.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { SignatureHelp.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { SignatureHelp.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
