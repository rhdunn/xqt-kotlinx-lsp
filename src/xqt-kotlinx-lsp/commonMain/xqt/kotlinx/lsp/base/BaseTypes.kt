// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.*
import xqt.kotlinx.rpc.json.serialization.valueOutOfRange

/**
 * Defines an integer number in the range of -2^31 to 2^31 - 1.
 *
 * @since 1.0.0 (LSP 3.16.0)
 */
typealias Integer = JsonInt

/**
 * Defines an unsigned integer number in the range of 0 to 2^31 - 1.
 *
 * __Note:__ The upper bound is 2^31 - 1 instead of 2^32 - 1 because LSP
 * is following the Debug Adapter Protocol (DAP) definition. See
 * [LSP issue 1394](https://github.com/microsoft/language-server-protocol/issues/1394) and
 * [DAP issue 237](https://github.com/microsoft/debug-adapter-protocol/issues/237).
 *
 * @since 1.0.0 (LSP 3.16.0)
 */
object UInteger : JsonSerialization<UInt> {
    override fun serializeToJson(value: UInt): JsonPrimitive {
        if (value > Int.MAX_VALUE.toUInt()) valueOutOfRange(value)
        return JsonUInt.serializeToJson(value)
    }

    override fun deserialize(json: JsonElement): UInt {
        val value = JsonUInt.deserialize(json)
        if (value > Int.MAX_VALUE.toUInt()) valueOutOfRange(value)
        return value
    }
}

/**
 * Defines a decimal number.
 *
 * Since decimal numbers are very rare in the language server specification we
 * denote the exact range with every decimal using the mathematics interval
 * notation (e.g. `[0, 1]` denotes all decimals `d` with `0 <= d <= 1`).
 *
 * @since 1.0.0 (LSP 3.16.0)
 */
typealias Decimal = JsonDouble

/**
 * The LSP any type
 *
 * @since 1.0.0 (LSP 3.17.0)
 */
typealias LSPAny = xqt.kotlinx.rpc.json.serialization.types.JsonElement

/**
 * LSP object definition.
 *
 * @since 1.0.0 (LSP 3.17.0)
 */
typealias LSPObject = JsonObject

/**
 * LSP arrays.
 *
 * @since 1.0.0 (LSP 3.17.0)
 */
typealias LSPArray = JsonArray
