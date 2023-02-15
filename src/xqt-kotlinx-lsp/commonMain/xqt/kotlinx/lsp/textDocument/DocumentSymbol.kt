// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

/**
 * A symbol kind.
 *
 * @param kind the symbol kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class SymbolKind(val kind: Int) {
    companion object : JsonSerialization<SymbolKind> {
        override fun serializeToJson(value: SymbolKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): SymbolKind {
            return SymbolKind(JsonInt.deserialize(json))
        }

        /**
         * A file.
         */
        val File: SymbolKind = SymbolKind(1)

        /**
         * A module.
         */
        val Module: SymbolKind = SymbolKind(2)

        /**
         * A namespace.
         */
        val Namespace: SymbolKind = SymbolKind(3)

        /**
         * A package.
         */
        val Package: SymbolKind = SymbolKind(4)

        /**
         * A class.
         */
        val Class: SymbolKind = SymbolKind(5)

        /**
         * A method.
         */
        val Method: SymbolKind = SymbolKind(6)

        /**
         * A property.
         */
        val Property: SymbolKind = SymbolKind(7)

        /**
         * A field.
         */
        val Field: SymbolKind = SymbolKind(8)

        /**
         * A constructor.
         */
        val Constructor: SymbolKind = SymbolKind(9)

        /**
         * An enumeration.
         */
        val Enum: SymbolKind = SymbolKind(10)

        /**
         * An interface.
         */
        val Interface: SymbolKind = SymbolKind(11)

        /**
         * A function.
         */
        val Function: SymbolKind = SymbolKind(12)

        /**
         * A variable.
         */
        val Variable: SymbolKind = SymbolKind(13)

        /**
         * A constant.
         */
        val Constant: SymbolKind = SymbolKind(14)

        /**
         * A string.
         */
        val String: SymbolKind = SymbolKind(15)

        /**
         * A number.
         */
        val Number: SymbolKind = SymbolKind(16)

        /**
         * A boolean.
         */
        val Boolean: SymbolKind = SymbolKind(17)

        /**
         * An array.
         */
        val Array: SymbolKind = SymbolKind(18)
    }
}
