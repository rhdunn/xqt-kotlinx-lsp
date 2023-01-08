// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.window

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.window.MessageType
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The MessageType type")
class TheMessageTypeType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("1", MessageType.serializeToJson(MessageType.Error).toString())
        assertEquals("2", MessageType.serializeToJson(MessageType.Warning).toString())
        assertEquals("3", MessageType.serializeToJson(MessageType.Info).toString())
        assertEquals("4", MessageType.serializeToJson(MessageType.Log).toString())

        assertEquals("900", MessageType.serializeToJson(MessageType(900)).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(MessageType.Error, MessageType.deserialize(JsonPrimitive(1)))
        assertEquals(MessageType.Warning, MessageType.deserialize(JsonPrimitive(2)))
        assertEquals(MessageType.Info, MessageType.deserialize(JsonPrimitive(3)))
        assertEquals(MessageType.Log, MessageType.deserialize(JsonPrimitive(4)))

        assertEquals(MessageType(900), MessageType.deserialize(JsonPrimitive(900)))
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { MessageType.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { MessageType.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { MessageType.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { MessageType.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { MessageType.deserialize(JsonPrimitive("abc")) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'string'", e5.message)

        val e6 = assertFails { MessageType.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
