// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import kotlin.jvm.JvmInline

/**
 * The file event type.
 *
 * @param type the file event type.
 *
 * @since 1.0.0
 */
@JvmInline
value class FileChangeType(val type: Int) {
    companion object : JsonSerialization<FileChangeType> {
        override fun serializeToJson(value: FileChangeType): JsonPrimitive = JsonPrimitive(value.type)

        override fun deserialize(json: JsonElement): FileChangeType {
            return FileChangeType(JsonInt.deserialize(json))
        }

        /**
         * The file got created.
         */
        val Created: FileChangeType = FileChangeType(1)

        /**
         * The file got changed.
         */
        val Changed: FileChangeType = FileChangeType(2)

        /**
         * The file got deleted.
         */
        val Deleted: FileChangeType = FileChangeType(3)
    }
}
