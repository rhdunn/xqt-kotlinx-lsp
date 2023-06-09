// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonStringEnumerationType
import kotlin.jvm.JvmInline

/**
 * The level of verbosity with which the server reports its execution trace.
 *
 * @param kind the trace value kind.
 *
 * @since 3.0.0 (LSP 3.16.0)
 */
@JvmInline
value class TraceValue(override val kind: String) : JsonEnumeration<String> {
    override fun toString(): String = kind

    companion object : JsonStringEnumerationType<TraceValue>() {
        override fun valueOf(value: String): TraceValue = TraceValue(value)

        /**
         * Don't report any trace events.
         */
        val Off = TraceValue("off")

        /**
         * Report trace events at the "messages" level.
         */
        val Messages = TraceValue("messages")

        /**
         * Report trace events at the "verbose" level.
         */
        val Verbose = TraceValue("verbose")
    }
}
