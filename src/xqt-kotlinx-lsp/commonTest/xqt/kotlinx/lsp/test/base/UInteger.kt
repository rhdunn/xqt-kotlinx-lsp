// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.ValueOutOfRangeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The UInteger type")
class TheUIntegerType {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals(JsonPrimitive(1234), UInteger.serializeToJson(1234u))

        assertEquals("0", UInteger.serializeToJson(UInt.MIN_VALUE).toString())
        assertEquals("2147483647", UInteger.serializeToJson(Int.MAX_VALUE.toUInt()).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(1234u, UInteger.deserialize(JsonPrimitive(1234)))

        assertEquals(UInt.MIN_VALUE, UInteger.deserialize(JsonPrimitive(UInt.MIN_VALUE.toLong())))
        assertEquals(Int.MAX_VALUE.toUInt(), UInteger.deserialize(JsonPrimitive(Int.MAX_VALUE)))
    }

    @Test
    @DisplayName("throws an error if the value is out of range")
    fun throws_an_error_if_the_value_is_out_of_range() {
        val e1 = assertFails { UInteger.deserialize(JsonPrimitive(-1)) }
        assertEquals(ValueOutOfRangeException::class, e1::class)
        assertEquals("The value '-1' is out of range", e1.message)

        val e2 = assertFails { UInteger.deserialize(JsonPrimitive(Int.MAX_VALUE.toLong() + 1)) }
        assertEquals(ValueOutOfRangeException::class, e2::class)
        assertEquals("The value '2147483648' is out of range", e2.message)

        val e3 = assertFails { UInteger.serializeToJson(Int.MAX_VALUE.toUInt() + 1u) }
        assertEquals(ValueOutOfRangeException::class, e3::class)
        assertEquals("The value '2147483648' is out of range", e3.message)
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { UInteger.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { UInteger.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { UInteger.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { UInteger.deserialize(JsonPrimitive("test")) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'string'", e4.message)

        val e5 = assertFails { UInteger.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'boolean'", e5.message)

        val e6 = assertFails { UInteger.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
