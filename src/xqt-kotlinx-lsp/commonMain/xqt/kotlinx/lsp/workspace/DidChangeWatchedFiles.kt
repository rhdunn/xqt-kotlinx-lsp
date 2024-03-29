// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.workspace

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonIntEnumerationType
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonBoolean
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import kotlin.jvm.JvmInline

/**
 * Capabilities specific to the `workspace/didChangeWatchedFiles` notification.
 *
 * @since 3.0.0
 */
data class DidChangeWatchedFilesClientCapabilities(
    /**
     * Did change watched files notification supports dynamic registration.
     */
    val dynamicRegistration: Boolean? = null
) {
    companion object : JsonSerialization<DidChangeWatchedFilesClientCapabilities> {
        override fun serializeToJson(value: DidChangeWatchedFilesClientCapabilities): JsonObject = buildJsonObject {
            putOptional("dynamicRegistration", value.dynamicRegistration, JsonBoolean)
        }

        override fun deserialize(json: JsonElement): DidChangeWatchedFilesClientCapabilities = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DidChangeWatchedFilesClientCapabilities(
                dynamicRegistration = json.getOptional("dynamicRegistration", JsonBoolean)
            )
        }
    }
}

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
value class FileChangeType(val type: Int) : JsonEnumeration<Int> {
    override val kind: Int get() = type

    companion object : JsonIntEnumerationType<FileChangeType>() {
        override fun valueOf(value: Int): FileChangeType = FileChangeType(value)

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
     * The file's URI.
     */
    val uri: String,

    /**
     * The change type.
     */
    val type: FileChangeType
) {
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
 * the client detects changes to files watched by the language client.
 *
 * @since 1.0.0
 */
fun WorkspaceNotification.didChangeWatchedFiles(
    handler: DidChangeWatchedFilesParams.() -> Unit
): Unit = notification.method(
    method = WorkspaceNotification.DID_CHANGE_WATCHED_FILES,
    handler = handler,
    paramsSerializer = DidChangeWatchedFilesParams
)

/**
 * The watched files notification is sent from the client to the server when
 * the client detects changes to files watched by the language client.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeWatchedFiles(
    params: DidChangeWatchedFilesParams
): Unit = server.sendNotification(
    method = WorkspaceNotification.DID_CHANGE_WATCHED_FILES,
    params = DidChangeWatchedFilesParams.serializeToJson(params)
)

/**
 * The watched files notification is sent from the client to the server when
 * the client detects changes to files watched by the language client.
 *
 * @param changes the actual file events
 *
 * @since 1.0.0
 */
fun WorkspaceJsonRpcServer.didChangeWatchedFiles(
    changes: List<FileEvent>
): Unit = didChangeWatchedFiles(
    params = DidChangeWatchedFilesParams(changes = changes)
)
