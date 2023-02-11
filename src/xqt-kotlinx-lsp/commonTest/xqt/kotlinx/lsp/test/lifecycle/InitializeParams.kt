// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.lifecycle.InitializeParams
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The initialize request parameters")
class TheInitializeRequestParameters {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "processId" to JsonPrimitive(1234),
            "rootPath" to JsonNull,
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(1234, params.processId)
        assertEquals(null, params.rootPath)
        assertEquals(jsonObjectOf("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the root path property")
    fun supports_the_root_path_property() {
        val json = jsonObjectOf(
            "processId" to JsonPrimitive(1234),
            "rootPath" to JsonPrimitive("file:///home/lorem/ipsum.py"),
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(1234, params.processId)
        assertEquals("file:///home/lorem/ipsum.py", params.rootPath)
        assertEquals(jsonObjectOf("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { InitializeParams.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { InitializeParams.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { InitializeParams.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { InitializeParams.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { InitializeParams.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { InitializeParams.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
