// Copyright (C) 2022-2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.lifecycle.ClientCapabilities
import xqt.kotlinx.lsp.lifecycle.InitializeParams
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonProperty
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@Suppress("DEPRECATION")
@DisplayName("The initialize request parameters")
class TheInitializeRequestParameters {
    @Test
    @DisplayName("supports the non-optional properties for LSP 2.0.0")
    fun supports_the_non_optional_properties_for_lsp_2_0() {
        val json = jsonObjectOf(
            "processId" to JsonNull,
            "rootPath" to JsonNull,
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(null, params.processId)
        assertEquals(JsonProperty<String>(null), params.rootPath)
        assertEquals(null, params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "processId" to JsonNull,
            "rootPath" to JsonNull,
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(null, params.processId)
        assertEquals(JsonProperty<String>(null), params.rootPath)
        assertEquals(JsonProperty.missing(), params.rootUri)
        assertEquals(null, params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the process ID property for LSP 3.0.0")
    fun supports_the_process_id_property_for_lsp_3_0() {
        val json = jsonObjectOf(
            "processId" to JsonPrimitive(1234),
            "rootUri" to JsonNull,
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(1234, params.processId)
        assertEquals(JsonProperty.missing(), params.rootPath)
        assertEquals(JsonProperty<DocumentUri>(null), params.rootUri)
        assertEquals(null, params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the root path property")
    fun supports_the_root_path_property() {
        val json = jsonObjectOf(
            "processId" to JsonNull,
            "rootPath" to JsonPrimitive("file:///home/lorem/ipsum.py"),
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(null, params.processId)
        assertEquals("file:///home/lorem/ipsum.py", params.rootPath.value)
        assertEquals(JsonProperty.missing(), params.rootUri)
        assertEquals(null, params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the root uri property")
    fun supports_the_root_uri_property() {
        val json = jsonObjectOf(
            "processId" to JsonNull,
            "rootUri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(null, params.processId)
        assertEquals(JsonProperty.missing(), params.rootPath)
        assertEquals(DocumentUri("file:///home/lorem/ipsum.py"), params.rootUri.value)
        assertEquals(null, params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

        assertEquals(json, InitializeParams.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the initialization options property")
    fun supports_the_initialization_options_property() {
        val json = jsonObjectOf(
            "processId" to JsonNull,
            "rootPath" to JsonNull,
            "initializationOptions" to JsonPrimitive("test"),
            "capabilities" to jsonObjectOf(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )

        val params = InitializeParams.deserialize(json)
        assertEquals(null, params.processId)
        assertEquals(JsonProperty<String>(null), params.rootPath)
        assertEquals(JsonProperty.missing(), params.rootUri)
        assertEquals(JsonPrimitive("test"), params.initializationOptions)
        assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), params.capabilities)

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
