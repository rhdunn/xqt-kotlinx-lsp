// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.base

import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.rpc.json.protocol.ErrorCode
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("The LSP error codes")
class TheLspErrorCodes {
    @Test
    @DisplayName("can serialize to JSON")
    fun can_serialize_to_json() {
        assertEquals("-32700", ErrorCode.serializeToJson(ErrorCodes.ParseError).toString())

        assertEquals("-32603", ErrorCode.serializeToJson(ErrorCodes.InternalError).toString())
        assertEquals("-32602", ErrorCode.serializeToJson(ErrorCodes.InvalidParams).toString())
        assertEquals("-32601", ErrorCode.serializeToJson(ErrorCodes.MethodNotFound).toString())
        assertEquals("-32600", ErrorCode.serializeToJson(ErrorCodes.InvalidRequest).toString())

        assertEquals("-32099", ErrorCode.serializeToJson(ErrorCodes.ServerErrorStart).toString())
        assertEquals("-32000", ErrorCode.serializeToJson(ErrorCodes.ServerErrorEnd).toString())

        assertEquals("-32002", ErrorCode.serializeToJson(ErrorCodes.ServerNotInitialized).toString())
        assertEquals("-32001", ErrorCode.serializeToJson(ErrorCodes.UnknownErrorCode).toString())
    }

    @Test
    @DisplayName("can deserialize from JSON")
    fun can_deserialize_from_json() {
        assertEquals(ErrorCodes.ParseError, ErrorCode.deserialize(JsonPrimitive(-32700)))

        assertEquals(ErrorCodes.InternalError, ErrorCode.deserialize(JsonPrimitive(-32603)))
        assertEquals(ErrorCodes.InvalidParams, ErrorCode.deserialize(JsonPrimitive(-32602)))
        assertEquals(ErrorCodes.MethodNotFound, ErrorCode.deserialize(JsonPrimitive(-32601)))
        assertEquals(ErrorCodes.InvalidRequest, ErrorCode.deserialize(JsonPrimitive(-32600)))

        assertEquals(ErrorCodes.ServerErrorStart, ErrorCode.deserialize(JsonPrimitive(-32099)))
        assertEquals(ErrorCodes.ServerErrorEnd, ErrorCode.deserialize(JsonPrimitive(-32000)))

        assertEquals(ErrorCodes.ServerNotInitialized, ErrorCode.deserialize(JsonPrimitive(-32002)))
        assertEquals(ErrorCodes.UnknownErrorCode, ErrorCode.deserialize(JsonPrimitive(-32001)))
    }

    @Test
    @DisplayName("map to JSON-RPC error codes")
    fun map_to_json_rpc_error_codes() {
        assertEquals(ErrorCode.ParseError, ErrorCodes.ParseError)

        assertEquals(ErrorCode.InternalError, ErrorCodes.InternalError)
        assertEquals(ErrorCode.InvalidParams, ErrorCodes.InvalidParams)
        assertEquals(ErrorCode.MethodNotFound, ErrorCodes.MethodNotFound)
        assertEquals(ErrorCode.InvalidRequest, ErrorCodes.InvalidRequest)

        assertEquals(ErrorCode.ServerErrorRangeStart, ErrorCodes.ServerErrorStart)
        assertEquals(ErrorCode.ServerErrorRangeEnd, ErrorCodes.ServerErrorEnd)
    }
}
