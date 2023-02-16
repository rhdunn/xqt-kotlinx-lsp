// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import kotlin.jvm.JvmInline

/**
 * Represents information about programming constructs like variables, classes,
 * and interfaces.
 *
 * @since 1.0.0
 */
data class SymbolInformation(
    /**
     * The name of this symbol.
     */
    val name: String,

    /**
     * The kind of this symbol.
     */
    val kind: SymbolKind,

    /**
     * The location of this symbol.
     */
    val location: Location,

    /**
     * The name of the symbol containing this symbol.
     */
    val containerName: String? = null
) {
    companion object : JsonSerialization<SymbolInformation> {
        override fun serializeToJson(value: SymbolInformation): JsonObject = buildJsonObject {
            put("name", value.name, JsonString)
            put("kind", value.kind, SymbolKind)
            put("location", value.location, Location)
            putOptional("containerName", value.containerName, JsonString)
        }

        override fun deserialize(json: JsonElement): SymbolInformation = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> SymbolInformation(
                name = json.get("name", JsonString),
                kind = json.get("kind", SymbolKind),
                location = json.get("location", Location),
                containerName = json.getOptional("containerName", JsonString)
            )
        }
    }
}

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
