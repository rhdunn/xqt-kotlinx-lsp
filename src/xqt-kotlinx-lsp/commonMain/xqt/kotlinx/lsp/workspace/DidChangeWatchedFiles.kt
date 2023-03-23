// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonInt
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import kotlin.jvm.JvmInline

/**
 * Parameters for `workspace/didChangeWatchedFiles` notification.
 *
 * @since 1.0.0
 */
data class DidChangeWatchedFilesParams(
    /**
     * The actual file events.
     */
    val changes: List<FileEvent>
) {
    companion object : JsonSerialization<DidChangeWatchedFilesParams> {
        private val FileEventArray = JsonTypedArray(FileEvent)

        override fun serializeToJson(value: DidChangeWatchedFilesParams): JsonObject = buildJsonObject {
            put("changes", value.changes, FileEventArray)
        }

        override fun deserialize(json: JsonElement): DidChangeWatchedFilesParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidChangeWatchedFilesParams(
                changes = json.get("changes", FileEventArray)
            )
        }
    }
}

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

/**
 * An event describing a file change.
 *
 * @since 1.0.0
 */
data class FileEvent(
    /**
     * The file's uri.
     */
    override val uri: String,

    /**
     * The change type.
     */
    val type: FileChangeType
) : TextDocumentIdentifier {
    companion object : JsonSerialization<FileEvent> {
        override fun serializeToJson(value: FileEvent): JsonObject = buildJsonObject {
            put("uri", value.uri, JsonString)
            put("type", value.type, FileChangeType)
        }

        override fun deserialize(json: JsonElement): FileEvent = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> FileEvent(
                uri = json.get("uri", JsonString),
                type = json.get("type", FileChangeType)
            )
        }
    }
}

/**
 * The watched files notification is sent from the client to the server when
 * the client detects changes to file watched by the language client.
 *
 * @since 1.0.0
 */
fun WorkspaceNotification.didChangeWatchedFiles(handler: DidChangeWatchedFilesParams.() -> Unit) {
    if (notification.method == WorkspaceNotification.DID_CHANGE_WATCHED_FILES) {
        notification.params(DidChangeWatchedFilesParams).handler()
    }
}
