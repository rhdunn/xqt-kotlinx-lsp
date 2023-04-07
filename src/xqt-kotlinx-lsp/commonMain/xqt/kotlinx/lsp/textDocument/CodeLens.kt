// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.lsp.types.Command
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Code lens options.
 *
 * @since 1.0.0
 */
data class CodeLensOptions(
    /**
     * The server provides support to resolve additional information for a
     * code lens.
     */
    val resolveProvider: Boolean? = null
) {
    companion object : JsonSerialization<CodeLensOptions> {
        override fun serializeToJson(value: CodeLensOptions): JsonObject = buildJsonObject {
            putOptional("resolveProvider", value.resolveProvider, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): CodeLensOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CodeLensOptions(
                resolveProvider = json.getOptional("resolveProvider", JsonBoolean)
            )
        }
    }
}

/**
 * A code lens represents a command that should be shown along with source text.
 *
 * For example, like the number of references, a way to run tests, etc.
 *
 * A code lens is _unresolved_ when no command is associated to it. For performance
 * reasons the creation of a code lens and resolving should be done to two stages.
 *
 * @since 1.0.0
 */
data class CodeLens(
    /**
     * The range in which this code lens is valid.
     *
     * This should only span a single line.
     */
    val range: Range,

    /**
     * The command this code lens represents.
     */
    val command: Command? = null,

    /**
     * A data entry field that is preserved on a code lens item between
     * a code lens and a code lens resolve request.
     */
    val data: JsonElement? = null
) {
    companion object : JsonSerialization<CodeLens> {
        override fun serializeToJson(value: CodeLens): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            putOptional("command", value.command, Command)
            putOptional("data", value.data, LSPAny)
        }

        override fun deserialize(json: JsonElement): CodeLens = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CodeLens(
                range = json.get("range", Range),
                command = json.getOptional("command", Command),
                data = json.getOptional("data", LSPAny),
            )
        }
    }
}

private val CodeLensArray = JsonTypedArray(CodeLens)

/**
 * The code lens request is sent from the client to the server to compute code lenses for
 * a given text document.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.codeLens(handler: TextDocumentIdentifier.() -> List<CodeLens>) {
    if (request.method == TextDocumentRequest.CODE_LENS) {
        val result = request.params(TextDocumentIdentifier).handler()
        request.sendResult(result, CodeLensArray)
    }
}
