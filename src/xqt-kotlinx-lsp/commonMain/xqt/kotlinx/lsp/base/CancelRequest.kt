// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendNotification
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * Parameters for the `$/cancelRequest` notification.
 *
 * @since 2.0.0
 */
data class CancelParams(
    /**
     * The request id to cancel.
     */
    val id: JsonIntOrString
) {
    companion object : JsonSerialization<CancelParams> {
        override fun serializeToJson(value: CancelParams): JsonObject = buildJsonObject {
            put("id", value.id, JsonIntOrString)
        }

        override fun deserialize(json: JsonElement): CancelParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CancelParams(
                id = json.get("id", JsonIntOrString)
            )
        }
    }
}

/**
 * Cancel an active request.
 *
 * A request that got canceled still needs to return from the server and send a response
 * back. It can not be left open/hanging. This is in line with the JSON RPC protocol that
 * requires that every request sends a response back. In addition, it allows for returning
 * partial results on cancel.
 *
 * @since 2.0.0
 */
fun DollarNotification.cancelRequest(
    handler: CancelParams.() -> Unit
): Unit = notification.method(
    method = DollarNotification.CANCEL_REQUEST,
    handler = handler,
    paramsSerializer = CancelParams
)

/**
 * Cancel an active request.
 *
 * A request that got canceled still needs to return from the server and send a response
 * back. It can not be left open/hanging. This is in line with the JSON RPC protocol that
 * requires that every request sends a response back. In addition, it allows for returning
 * partial results on cancel.
 *
 * @param params the notification parameters
 *
 * @since 2.0.0
 */
fun DollarJsonRpcServer.cancelRequest(
    params: CancelParams
): Unit = server.sendNotification(
    method = DollarNotification.CANCEL_REQUEST,
    params = CancelParams.serializeToJson(params)
)

/**
 * Cancel an active request.
 *
 * A request that got canceled still needs to return from the server and send a response
 * back. It can not be left open/hanging. This is in line with the JSON RPC protocol that
 * requires that every request sends a response back. In addition, it allows for returning
 * partial results on cancel.
 *
 * @param id the request id to cancel
 *
 * @since 2.0.0
 */
fun DollarJsonRpcServer.cancelRequest(
    id: JsonIntOrString
): Unit = cancelRequest(
    params = CancelParams(id = id)
)
