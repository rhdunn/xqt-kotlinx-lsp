// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import kotlin.jvm.JvmInline

/**
 * Represents a diagnostic, such as a compiler error or warning.
 *
 * Diagnostic objects are only valid in the scope of a resource.
 *
 * @since 1.0.0
 */
data class Diagnostic(
    /**
     * The range at which the message applies.
     */
    val range: Range,

    /**
     * The diagnostic's severity.
     *
     * Can be omitted. If omitted it is up to the client to interpret
     * diagnostics as error, warning, info or hint.
     */
    val severity: DiagnosticSeverity? = null,

    /**
     * The diagnostic's code, which might appear in the user interface.
     */
    val code: JsonIntOrString? = null,

    /**
     * A human-readable string describing the source of this
     * diagnostic, e.g. 'typescript' or 'super lint'.
     */
    val source: String? = null,

    /**
     * The diagnostic's message.
     */
    val message: String
) {
    companion object : JsonSerialization<Diagnostic> {
        override fun serializeToJson(value: Diagnostic): JsonObject = buildJsonObject {
            put("range", value.range, Range)
            putOptional("severity", value.severity, DiagnosticSeverity)
            putOptional("code", value.code, JsonIntOrString)
            putOptional("source", value.source, JsonString)
            put("message", value.message, JsonString)
        }

        override fun deserialize(json: JsonElement): Diagnostic = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> Diagnostic(
                range = json.get("range", Range),
                severity = json.getOptional("severity", DiagnosticSeverity),
                code = json.getOptional("code", JsonIntOrString),
                source = json.getOptional("source", JsonString),
                message = json.get("message", JsonString)
            )
        }
    }
}

/**
 * The diagnostic severities.
 *
 * @param severity the diagnostic severity.
 *
 * @since 1.0.0
 */
@JvmInline
value class DiagnosticSeverity(val severity: Int) {
    companion object : JsonSerialization<DiagnosticSeverity> {
        override fun serializeToJson(value: DiagnosticSeverity): JsonPrimitive = JsonPrimitive(value.severity)

        override fun deserialize(json: JsonElement): DiagnosticSeverity {
            return DiagnosticSeverity(JsonInt.deserialize(json))
        }

        /**
         * Reports an error.
         */
        val Error: DiagnosticSeverity = DiagnosticSeverity(1)

        /**
         * Reports a warning.
         */
        val Warning: DiagnosticSeverity = DiagnosticSeverity(2)

        /**
         * Reports an information.
         */
        val Information: DiagnosticSeverity = DiagnosticSeverity(3)

        /**
         * Reports a hint.
         */
        val Hint: DiagnosticSeverity = DiagnosticSeverity(4)
    }
}
