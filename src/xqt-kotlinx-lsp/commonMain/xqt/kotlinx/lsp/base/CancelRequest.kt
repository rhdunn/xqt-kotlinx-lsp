// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
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
