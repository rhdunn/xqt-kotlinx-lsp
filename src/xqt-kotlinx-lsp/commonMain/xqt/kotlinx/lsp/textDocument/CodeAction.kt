// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Diagnostic
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

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
