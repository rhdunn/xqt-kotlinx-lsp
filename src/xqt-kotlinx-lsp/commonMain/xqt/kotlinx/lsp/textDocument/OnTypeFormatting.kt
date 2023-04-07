// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.protocol.TypedResponseObject
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendRequest
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Format document on type options.
 *
 * @since 1.0.0
 */
data class DocumentOnTypeFormattingOptions(
    /**
     * A character on which formatting should be triggered, like `}`.
     */
    val firstTriggerCharacter: String,

    /**
     * More trigger characters.
     */
    val moreTriggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<DocumentOnTypeFormattingOptions> {
        private val JsonStringArray = JsonTypedArray(JsonString)

        override fun serializeToJson(value: DocumentOnTypeFormattingOptions): JsonObject = buildJsonObject {
            put("firstTriggerCharacter", value.firstTriggerCharacter, JsonString)
            // NOTE: The moreTriggerCharacters property is mistyped in the LSP specification.
            putOptional("moreTriggerCharacter", value.moreTriggerCharacters, JsonStringArray)
        }

        override fun deserialize(json: JsonElement): DocumentOnTypeFormattingOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentOnTypeFormattingOptions(
                firstTriggerCharacter = json.get("firstTriggerCharacter", JsonString),
                // NOTE: The moreTriggerCharacters property is mistyped in the LSP specification.
                moreTriggerCharacters = json.getOptional("moreTriggerCharacter", JsonStringArray)
            )
        }
    }
}

/**
 * The `textDocument/onTypeFormatting` request parameters.
 *
 * @since 1.0.0
 */
data class DocumentOnTypeFormattingParams(
    /**
     * The document to format.
     */
    val textDocument: TextDocumentIdentifier,

    /**
     * The position at which this request was send.
     */
    val position: Position,

    /**
     * The character that has been typed.
     */
    val ch: String,

    /**
     * The format options.
     */
    val options: FormattingOptions
) {
    companion object : JsonSerialization<DocumentOnTypeFormattingParams> {
        override fun serializeToJson(value: DocumentOnTypeFormattingParams): JsonObject = buildJsonObject {
            put("textDocument", value.textDocument, TextDocumentIdentifier)
            put("position", value.position, Position)
            put("ch", value.ch, JsonString)
            put("options", value.options, FormattingOptions)
        }

        override fun deserialize(json: JsonElement): DocumentOnTypeFormattingParams = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> DocumentOnTypeFormattingParams(
                textDocument = json.get("textDocument", TextDocumentIdentifier),
                position = json.get("position", Position),
                ch = json.get("ch", JsonString),
                options = json.get("options", FormattingOptions)
            )
        }
    }
}

private val TextEditArray = JsonTypedArray(TextEdit)

/**
 * The document on type formatting request is sent from the client to the server to format
 * parts of the document during typing.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.onTypeFormatting(handler: DocumentOnTypeFormattingParams.() -> List<TextEdit>) {
    if (request.method == TextDocumentRequest.ON_TYPE_FORMATTING) {
        val result = request.params(DocumentOnTypeFormattingParams).handler()
        request.sendResult(result, TextEditArray)
    }
}

/**
 * The document on type formatting request is sent from the client to the server to format
 * parts of the document during typing.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.onTypeFormatting(
    params: DocumentOnTypeFormattingParams,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.ON_TYPE_FORMATTING,
    params = DocumentOnTypeFormattingParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = FormattingResponse
)

/**
 * The document on type formatting request is sent from the client to the server to format
 * parts of the document during typing.
 *
 * @param textDocument the document in which the command was invoked
 * @param position the position at which this request was send
 * @param ch the character that has been typed
 * @param options the format options
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 1.0.0
 */
fun TextDocumentJsonRpcServer.onTypeFormatting(
    textDocument: TextDocumentIdentifier,
    position: Position,
    ch: String,
    options: FormattingOptions,
    responseHandler: (TypedResponseObject<List<TextEdit>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = onTypeFormatting(
    params = DocumentOnTypeFormattingParams(
        textDocument = textDocument,
        position = position,
        ch = ch,
        options = options
    ),
    responseHandler = responseHandler
)
