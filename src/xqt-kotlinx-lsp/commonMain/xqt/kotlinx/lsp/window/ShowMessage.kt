// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.window

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.enumeration.JsonEnumeration
import xqt.kotlinx.rpc.json.enumeration.JsonIntEnumerationType
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import kotlin.jvm.JvmInline

/**
 * Parameters for `window/showMessage` notifications.
 *
 * @since 1.0.0
 */
data class ShowMessageParams(
    /**
     * The message type.
     */
    val type: MessageType,

    /**
     * The actual message.
     */
    val message: String
) {
    companion object : JsonSerialization<ShowMessageParams> {
        override fun serializeToJson(value: ShowMessageParams): JsonObject = buildJsonObject {
            put("type", value.type, MessageType)
            put("message", value.message, JsonString)
        }

        override fun deserialize(json: JsonElement): ShowMessageParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ShowMessageParams(
                type = json.get("type", MessageType),
                message = json.get("message", JsonString)
            )
        }
    }
}

/**
 * Parameters for the `window/showMessageRequest` request.
 *
 * @since 2.0.0
 */
data class ShowMessageRequestParams(
    /**
     * The message type.
     */
    val type: MessageType,

    /**
     * The actual message.
     */
    val message: String,

    /**
     * The message action items to present.
     */
    val actions: List<MessageActionItem>
) {
    companion object : JsonSerialization<ShowMessageRequestParams> {
        override fun serializeToJson(value: ShowMessageRequestParams): JsonObject = buildJsonObject {
            put("type", value.type, MessageType)
            put("message", value.message, JsonString)
            putOptional("actions", value.actions, MessageActionItemArray)
        }

        override fun deserialize(json: JsonElement): ShowMessageRequestParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ShowMessageRequestParams(
                type = json.get("type", MessageType),
                message = json.get("message", JsonString),
                actions = json.getOptional("actions", MessageActionItemArray)
            )
        }
    }
}

/**
 * The message type.
 *
 * @param type the message type.
 *
 * @since 1.0.0
 */
@JvmInline
value class MessageType(val type: Int) : JsonEnumeration<Int> {
    override val kind: Int get() = type

    companion object : JsonIntEnumerationType<MessageType>() {
        override fun valueOf(value: Int): MessageType = MessageType(value)

        /**
         * An error message.
         */
        val Error: MessageType = MessageType(1)

        /**
         * A warning message.
         */
        val Warning: MessageType = MessageType(2)

        /**
         * An information message.
         */
        val Info: MessageType = MessageType(3)

        /**
         * A log message.
         */
        val Log: MessageType = MessageType(4)
    }
}

/**
 * A message action item.
 *
 * @since 2.0.0
 */
data class MessageActionItem(
    /**
     * A short title like 'Retry', 'Open Log' etc.
     */
    val title: String
) {
    companion object : JsonSerialization<MessageActionItem> {
        override fun serializeToJson(value: MessageActionItem): JsonObject = buildJsonObject {
            put("title", value.title, JsonString)
        }

        override fun deserialize(json: JsonElement): MessageActionItem = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> MessageActionItem(
                title = json.get("title", JsonString)
            )
        }
    }
}

private val MessageActionItemArray = JsonTypedArray(MessageActionItem)

/**
 * The response of a show message request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class ShowMessageResponse(
    override val id: JsonIntOrString?,
    override val result: MessageActionItem?,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<MessageActionItem?, JsonElement> {
    companion object : TypedResponseObjectConverter<MessageActionItem?, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<MessageActionItem?, JsonElement> {
            return ShowMessageResponse(
                id = response.id,
                result = response.result?.let { MessageActionItem.deserialize(it) },
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @since 1.0.0
 */
fun WindowNotification.showMessage(
    handler: ShowMessageParams.() -> Unit
): Unit = notification.method(
    method = WindowNotification.SHOW_MESSAGE,
    handler = handler,
    paramsSerializer = ShowMessageParams
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * In addition to the show message notification the request allows to pass actions
 * and to wait for an answer from the client.
 *
 * @since 2.0.0
 */
fun WindowRequest.showMessage(
    handler: ShowMessageRequestParams.() -> MessageActionItem
): Unit = request.method(
    method = WindowRequest.SHOW_MESSAGE,
    handler = handler,
    paramsSerializer = ShowMessageRequestParams,
    resultSerializer = MessageActionItem
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @param params the notification parameters
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.showMessage(
    params: ShowMessageParams
): Unit = server.sendNotification(
    method = WindowNotification.SHOW_MESSAGE,
    params = ShowMessageParams.serializeToJson(params)
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * In addition to the show message notification the request allows to pass actions
 * and to wait for an answer from the client.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun WindowJsonRpcServer.showMessage(
    params: ShowMessageRequestParams,
    responseHandler: (TypedResponseObject<MessageActionItem?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = WindowRequest.SHOW_MESSAGE,
    params = ShowMessageRequestParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = ShowMessageResponse
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * @param type the message type
 * @param message the actual message
 *
 * @since 1.0.0
 */
fun WindowJsonRpcServer.showMessage(
    type: MessageType,
    message: String
): Unit = showMessage(
    params = ShowMessageParams(
        type = type,
        message = message
    )
)

/**
 * Ask the client to display a particular message in the user interface.
 *
 * In addition to the show message notification the request allows to pass actions
 * and to wait for an answer from the client.
 *
 * @param type the message type
 * @param message the actual message
 * @param actions the message action items to present
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun WindowJsonRpcServer.showMessage(
    type: MessageType,
    message: String,
    actions: List<MessageActionItem> = listOf(),
    responseHandler: (TypedResponseObject<MessageActionItem?, JsonElement>.() -> Unit)? = null
): JsonIntOrString = showMessage(
    params = ShowMessageRequestParams(
        type = type,
        message = message,
        actions = actions
    ),
    responseHandler = responseHandler
)
