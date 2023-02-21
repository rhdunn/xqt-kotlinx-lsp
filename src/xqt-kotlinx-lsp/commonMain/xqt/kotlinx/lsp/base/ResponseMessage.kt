// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import kotlinx.serialization.json.JsonElement
import xqt.kotlinx.rpc.json.protocol.ErrorCode
import xqt.kotlinx.rpc.json.protocol.ErrorObject
import xqt.kotlinx.rpc.json.protocol.ResponseObject

/**
 * A number that indicates the error type that occurred.
 *
 * @since 1.0.0
 */
object ErrorCodes {
    /**
     * Invalid JSON was received by the server.
     *
     * An error occurred on the server while parsing the JSON text.
     */
    val ParseError: ErrorCode = ErrorCode(-32700)

    /**
     * Invalid method parameter(s).
     */
    val InternalError: ErrorCode = ErrorCode(-32603)

    /**
     * Invalid method parameter(s).
     */
    val InvalidParams: ErrorCode = ErrorCode(-32602)

    /**
     * The method does not exist / is not available.
     */
    val MethodNotFound: ErrorCode = ErrorCode(-32601)

    /**
     * The JSON sent is not a valid Request object.
     */
    val InvalidRequest: ErrorCode = ErrorCode(-32600)

    /**
     * Reserved for implementation-defined server-errors.
     */
    val ServerErrorStart: ErrorCode = ErrorCode(-32099)

    /**
     * Reserved for implementation-defined server-errors.
     */
    val ServerErrorEnd: ErrorCode = ErrorCode(-32000)
}

/**
 * An error processing an RPC call.
 *
 * @since 1.0.0
 */
typealias ResponseError = ErrorObject

/**
 * A response message sent as a result of a request.
 *
 * If a request doesn't provide a result value the receiver of a request still
 * needs to return a response message to conform to the JSON-RPC specification.
 * The result property should be set to `null` in this case to signal a
 * successful request.
 *
 * @since 1.0.0
 */
typealias ResponseMessage = ResponseObject

/**
 * Invalid JSON was received by the server.
 *
 * An error occurred on the server while parsing the JSON text.
 *
 * @param message a string providing a short description of the error
 * @param data a primitive or structured value that contains additional
 *             information about the error
 */
@Suppress("FunctionName")
fun ParseError(message: String? = null, data: JsonElement? = null): ErrorObject = ErrorObject(
    code = ErrorCodes.ParseError,
    message = message ?: "Parse Error",
    data = data
)

/**
 * Invalid method parameter(s).
 *
 * @param message a string providing a short description of the error
 * @param data a primitive or structured value that contains additional
 *             information about the error
 */
@Suppress("FunctionName")
fun InternalError(message: String? = null, data: JsonElement? = null): ErrorObject = ErrorObject(
    code = ErrorCodes.InternalError,
    message = message ?: "Internal Error",
    data = data
)

/**
 * Invalid method parameter(s).
 *
 * @param message a string providing a short description of the error
 * @param data a primitive or structured value that contains additional
 *             information about the error
 */
@Suppress("FunctionName")
fun InvalidParams(message: String? = null, data: JsonElement? = null): ErrorObject = ErrorObject(
    code = ErrorCodes.InvalidParams,
    message = message ?: "Invalid Parameters",
    data = data
)

/**
 * The method does not exist / is not available.
 *
 * @param message a string providing a short description of the error
 * @param data a primitive or structured value that contains additional
 *             information about the error
 */
@Suppress("FunctionName")
fun MethodNotFound(message: String? = null, data: JsonElement? = null): ErrorObject = ErrorObject(
    code = ErrorCodes.ParseError,
    message = message ?: "Method Not Found",
    data = data
)

/**
 * The JSON sent is not a valid Request object.
 *
 * @param message a string providing a short description of the error
 * @param data a primitive or structured value that contains additional
 *             information about the error
 */
@Suppress("FunctionName")
fun InvalidRequest(message: String? = null, data: JsonElement? = null): ErrorObject = ErrorObject(
    code = ErrorCodes.InvalidRequest,
    message = message ?: "Invalid Request",
    data = data
)
