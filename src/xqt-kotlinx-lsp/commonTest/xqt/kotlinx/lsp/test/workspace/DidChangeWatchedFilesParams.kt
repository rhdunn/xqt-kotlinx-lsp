// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.workspace

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.workspace.DidChangeWatchedFilesParams
import xqt.kotlinx.lsp.workspace.FileChangeType
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The workspace/didChangeWatchedFiles notification parameters")
class TheWorkspaceDidChangeWatchedFilesNotificationParameters {
    @Test
    @DisplayName("supports the non-optional properties")
    fun supports_the_non_optional_properties() {
        val json = jsonObjectOf(
            "changes" to jsonArrayOf(
                jsonObjectOf(
                    "uri" to JsonPrimitive("file:///home/lorem/ipsum.py"),
                    "type" to JsonPrimitive(2)
                )
            )
        )

        val params = DidChangeWatchedFilesParams.deserialize(json)
        assertEquals(1, params.changes.size)

        assertEquals("file:///home/lorem/ipsum.py", params.changes[0].uri)
        assertEquals(FileChangeType.Changed, params.changes[0].type)

        assertEquals(json, DidChangeWatchedFilesParams.serializeToJson(params))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DidChangeWatchedFilesParams.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'array'", e1.message)

        val e2 = assertFails { DidChangeWatchedFilesParams.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'null'", e2.message)

        val e3 = assertFails { DidChangeWatchedFilesParams.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'string'", e3.message)

        val e4 = assertFails { DidChangeWatchedFilesParams.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DidChangeWatchedFilesParams.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DidChangeWatchedFilesParams.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
