// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.workspace

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.workspace.WorkspaceClientCapabilities
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The workspace client capabilities")
class TheWorkspaceClientCapabilities {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf()

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(null, params.applyEdit)
        assertEquals(null, params.didChangeConfiguration)
        assertEquals(null, params.didChangeWatchedFiles)
        assertEquals(null, params.symbol)
        assertEquals(null, params.executeCommand)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the apply edit property")
    fun supports_the_apply_edit_property() {
        val json = jsonObjectOf(
            "applyEdit" to JsonPrimitive(true)
        )

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(true, params.applyEdit)
        assertEquals(null, params.didChangeConfiguration)
        assertEquals(null, params.didChangeWatchedFiles)
        assertEquals(null, params.symbol)
        assertEquals(null, params.executeCommand)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the did change configuration property")
    fun supports_the_did_change_configuration_property() {
        val json = jsonObjectOf(
            "didChangeConfiguration" to jsonObjectOf(
                "dynamicRegistration" to JsonPrimitive(true)
            )
        )

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(null, params.applyEdit)
        assertEquals(true, params.didChangeConfiguration?.dynamicRegistration)
        assertEquals(null, params.didChangeWatchedFiles)
        assertEquals(null, params.symbol)
        assertEquals(null, params.executeCommand)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the did change watched files property")
    fun supports_the_did_change_watched_files_property() {
        val json = jsonObjectOf(
            "didChangeWatchedFiles" to jsonObjectOf(
                "dynamicRegistration" to JsonPrimitive(true)
            )
        )

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(null, params.applyEdit)
        assertEquals(null, params.didChangeConfiguration)
        assertEquals(true, params.didChangeWatchedFiles?.dynamicRegistration)
        assertEquals(null, params.symbol)
        assertEquals(null, params.executeCommand)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the symbol property")
    fun supports_the_symbol_property() {
        val json = jsonObjectOf(
            "symbol" to jsonObjectOf(
                "dynamicRegistration" to JsonPrimitive(true)
            )
        )

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(null, params.applyEdit)
        assertEquals(null, params.didChangeConfiguration)
        assertEquals(null, params.didChangeWatchedFiles)
        assertEquals(true, params.symbol?.dynamicRegistration)
        assertEquals(null, params.executeCommand)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("supports the execute command property")
    fun supports_the_execute_command_property() {
        val json = jsonObjectOf(
            "executeCommand" to jsonObjectOf(
                "dynamicRegistration" to JsonPrimitive(true)
            )
        )

        val params = WorkspaceClientCapabilities.deserialize(json)
        assertEquals(null, params.applyEdit)
        assertEquals(null, params.didChangeConfiguration)
        assertEquals(null, params.didChangeWatchedFiles)
        assertEquals(null, params.symbol)
        assertEquals(true, params.executeCommand?.dynamicRegistration)

        assertEquals(json, WorkspaceClientCapabilities.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { WorkspaceClientCapabilities.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { WorkspaceClientCapabilities.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { WorkspaceClientCapabilities.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { WorkspaceClientCapabilities.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { WorkspaceClientCapabilities.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { WorkspaceClientCapabilities.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
