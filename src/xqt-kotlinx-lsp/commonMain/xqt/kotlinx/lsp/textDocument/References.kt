// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Location
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * The `textDocument/references` request parameters.
 *
 * @since 1.0.0
 */
data class ReferencesParams(
    override val uri: String,
    override val position: Position,

    /**
     * The reference context.
     */
    val context: ReferenceContext
) : TextDocumentPosition {
    companion object : JsonSerialization<ReferencesParams> {
        override fun serializeToJson(value: ReferencesParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("position", value.position, Position)
            put("context", value.context, ReferenceContext)
        }

        override fun deserialize(json: JsonElement): ReferencesParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferencesParams(
                uri = json.get("uri", JsonString),
                position = json.get("position", Position),
                context = json.get("context", ReferenceContext)
            )
        }
    }
}

/**
 * The reference context.
 *
 * @since 1.0.0
 */
data class ReferenceContext(
    /**
     * Include the declaration of the current symbol.
     */
    val includeDeclaration: Boolean
) {
    companion object : JsonSerialization<ReferenceContext> {
        override fun serializeToJson(value: ReferenceContext): JsonObject = buildJsonObject {
            put("includeDeclaration", value.includeDeclaration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): ReferenceContext = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ReferenceContext(
                includeDeclaration = json.get("includeDeclaration", JsonBoolean)
            )
        }
    }
}

private val LocationArray = JsonTypedArray(Location)

/**
 * The references request is sent from the client to the server to resolve project-wide
 * references for the symbol denoted by the given text document position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.references(handler: ReferencesParams.() -> List<Location>) {
    if (request.method == TextDocumentRequest.REFERENCES) {
        val result = request.params(ReferencesParams).handler()
        request.sendResult(result, LocationArray)
    }
}
