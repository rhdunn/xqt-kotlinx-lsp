// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Represents a goto response result object.
 *
 * NOTE: This is an LSP extension designed to encapsulate the different result
 * types of the Goto Definition response.
 *
 * @since 1.0.0
 */
data class GoTo(
    /**
     * The locations of the definition to go to.
     */
    val locations: List<Location> = listOf()
) : List<Location> by locations {
    constructor(location: Location) : this(listOf(location))
    constructor(vararg locations: Location) : this(listOf(*locations))

    /**
     * The location of the definition to go to.
     *
     * If there are more than one location to go to, this is the first location
     * in the list.
     */
    val location: Location?
        get() = locations.firstOrNull()

    companion object : JsonSerialization<GoTo> {
        private val LocationArray = JsonTypedArray(Location)

        override fun serializeToJson(value: GoTo): JsonElement = when (value.locations.size) {
            1 -> Location.serializeToJson(value.locations[0])
            else -> LocationArray.serializeToJson(value.locations)
        }

        override fun deserialize(json: JsonElement): GoTo = when (json) {
            is JsonObject -> GoTo(Location.deserialize(json))
            is JsonArray -> GoTo(LocationArray.deserialize(json))
            else -> unsupportedKindType(json)
        }
    }
}

/**
 * The goto definition request is sent from the client to the server to resolve the definition
 * location of a symbol at a given text document position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.definition(handler: TextDocumentPosition.() -> GoTo) {
    if (request.method == TextDocumentRequest.DEFINITION) {
        val result = request.params(TextDocumentPosition).handler()
        request.sendResult(result, GoTo)
    }
}
