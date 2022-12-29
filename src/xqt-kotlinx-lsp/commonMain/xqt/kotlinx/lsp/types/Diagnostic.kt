// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

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
