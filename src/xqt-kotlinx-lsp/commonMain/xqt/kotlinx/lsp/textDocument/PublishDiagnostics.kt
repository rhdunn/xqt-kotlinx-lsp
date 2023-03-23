// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Diagnostic
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Parameters for `textDocument/publishDiagnostics` notification.
 *
 * @since 1.0.0
 */
data class PublishDiagnosticsParams(
    /**
     * The URI for which diagnostic information is reported.
     */
    val uri: String,

    /**
     * An array of diagnostic information items.
     */
    val diagnostics: List<Diagnostic>
) {
    companion object : JsonSerialization<PublishDiagnosticsParams> {
        private val DiagnosticArray = JsonTypedArray(Diagnostic)

        override fun serializeToJson(value: PublishDiagnosticsParams): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("diagnostics", value.diagnostics, DiagnosticArray)
        }

        override fun deserialize(json: JsonElement): PublishDiagnosticsParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> PublishDiagnosticsParams(
                uri = json.get("uri", JsonString),
                diagnostics = json.get("diagnostics", DiagnosticArray)
            )
        }
    }
}

/**
 * The diagnostics notification is sent from the server to the client to signal results of
 * validation runs.
 *
 * @since 1.0.0
 */
fun TextDocumentNotification.publishDiagnostics(handler: PublishDiagnosticsParams.() -> Unit) {
    if (notification.method == TextDocumentNotification.PUBLISH_DIAGNOSTICS) {
        notification.params(PublishDiagnosticsParams).handler()
    }
}
