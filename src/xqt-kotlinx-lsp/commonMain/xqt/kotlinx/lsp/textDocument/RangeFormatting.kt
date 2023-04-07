// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Range
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.protocol.TypedResponseObject
import xqt.kotlinx.rpc.json.protocol.method
import xqt.kotlinx.rpc.json.protocol.sendRequest
import xqt.kotlinx.rpc.json.serialization.JsonSerialization
import xqt.kotlinx.rpc.json.serialization.get
import xqt.kotlinx.rpc.json.serialization.put
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray
import xqt.kotlinx.rpc.json.serialization.unsupportedKindType

/**
 * The `textDocument/rangeFormatting` request parameters.
 *
 * @since 1.0.0
 */
data class DocumentRangeFormattingParams(
    /**
     * The document to format.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The range to format.
     */
    val range: Range,

    /**
     * The format options.
     */
    val options: FormattingOptions
) {
    companion object : JsonSerialization<DocumentRangeFormattingParams> {
        override fun serializeToJson(value: DocumentRangeFormattingParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("range", value.range, Range)
            put("options", value.options, FormattingOptions)
        }

        override fun deserialize(json: JsonElement): DocumentRangeFormattingParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentRangeFormattingParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                range = json.get("range", Range),
                options = json.get("options", FormattingOptions)
            )
        }
    }
}

private val TextEditArray = JsonTypedArray(TextEdit)

/**
 * The document range formatting request is sent from the client to the server to format a
 * given range in a document.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.rangeFormatting(
    handler: DocumentRangeFormattingParams.() -> List<TextEdit>
): Unit = request.method(
    method = TextDocumentRequest.RANGE_FORMATTING,
    handler = handler,
    paramsSerializer = DocumentRangeFormattingParams,
    resultSerializer = TextEditArray
)

/**
 * The document range formatting request is sent from the client to the server to format a
 * given range in a document.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.rangeFormatting(
    params: DocumentRangeFormattingParams,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.RANGE_FORMATTING,
    params = DocumentRangeFormattingParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = FormattingResponse
)

/**
 * The document range formatting request is sent from the client to the server to format a
 * given range in a document.
 *
 * @param textDocument the document in which the command was invoked
 * @param range rhe range to format
 * @param options the format options
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.rangeFormatting(
    textDocument: TextDocumentIdentifier,
    range: Range,
    options: FormattingOptions,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = rangeFormatting(
    params = DocumentRangeFormattingParams(
        textDocument = textDocument,
        range = range,
        options = options
    ),
    responseHandler = responseHandler
)
