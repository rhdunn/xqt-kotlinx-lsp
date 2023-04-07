// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Command
import xqt.kotlinx.lsp.types.Diagnostic
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
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

private val CommandArray = JsonTypedArray(Command)

/**
 * The code action request is sent from the client to the server to compute commands for
 * a given text document and range.
 *
 * The request is trigger when the user moves the cursor into an problem marker in the
 * editor or presses the lightbulb associated with a marker.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.codeAction(handler: CodeActionParams.() -> List<Command>) {
    if (request.method == TextDocumentRequest.CODE_ACTION) {
        val result = request.params(CodeActionParams).handler()
        request.sendResult(result, CommandArray)
    }
}
