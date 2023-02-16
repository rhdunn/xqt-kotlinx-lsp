// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Diagnostic
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * The `textDocument/codeAction` request parameters.
 *
 * @since 1.0.0
 */
data class CodeActionParams(
    /**
     * The document in which the command was invoked.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The range for which the command was invoked.
     */
    val range: Range,

    /**
     * Context carrying additional information.
     */
    val context: CodeActionContext
) {
    companion object : JsonSerialization<CodeActionParams> {
        override fun serializeToJson(value: CodeActionParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("range", value.range, Range)
            put("context", value.context, CodeActionContext)
        }

        override fun deserialize(json: JsonElement): CodeActionParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CodeActionParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                range = json.get("range", Range),
                context = json.get("context", CodeActionContext)
            )
        }
    }
}

/**
 * Contains additional diagnostic information about the context in which a code
 * action is run.
 *
 * @since 1.0.0
 */
data class CodeActionContext(
    /**
     * An array of diagnostics.
     */
    val diagnostics: List<Diagnostic>
) {
    companion object : JsonSerialization<CodeActionContext> {
        private val DiagnosticArray = JsonTypedArray(Diagnostic)

        override fun serializeToJson(value: CodeActionContext): JsonObject = buildJsonObject {
            put("diagnostics", value.diagnostics, DiagnosticArray)
        }

        override fun deserialize(json: JsonElement): CodeActionContext = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CodeActionContext(
                diagnostics = json.getOptional("diagnostics", DiagnosticArray)
            )
        }
    }
}
