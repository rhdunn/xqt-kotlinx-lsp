// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.LSPAny
import xqt.kotlinx.lsp.types.Position
import xqt.kotlinx.lsp.types.TextDocumentIdentifier
import xqt.kotlinx.lsp.types.TextDocumentPositionParams
import xqt.kotlinx.lsp.types.TextEdit
import xqt.kotlinx.rpc.json.protocol.*
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.*
import kotlin.jvm.JvmInline

/**
 * Completion options.
 *
 * @since 1.0.0
 */
data class CompletionOptions(
    /**
     * The server provides support to resolve additional information for a
     * completion item.
     */
    val resolveProvider: Boolean? = null,

    /**
     * The characters that trigger completion automatically.
     */
    val triggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<CompletionOptions> {
        private val JsonStringArray = JsonTypedArray(JsonString)

        override fun serializeToJson(value: CompletionOptions): JsonObject = buildJsonObject {
            putOptional("resolveProvider", value.resolveProvider, JsonBoolean)
            putOptional("triggerCharacters", value.triggerCharacters, JsonStringArray)
        }

        override fun deserialize(json: JsonElement): CompletionOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CompletionOptions(
                resolveProvider = json.getOptional("resolveProvider", JsonBoolean),
                triggerCharacters = json.getOptional("triggerCharacters", JsonStringArray)
            )
        }
    }
}

/**
 * Completion item.
 *
 * @since 1.0.0
 */
data class CompletionItem(
    /**
     * The label of this completion item.
     *
     * By default, this is also the text that is inserted when selecting this
     * completion.
     */
    val label: String,

    /**
     * The kind of this completion item.
     *
     * Based of the kind an icon is chosen by the editor.
     */
    val kind: CompletionItemKind? = null,

    /**
     * A human-readable string with additional information about this item.
     *
     * This can be things like type or symbol information.
     */
    val detail: String? = null,

    /**
     * A human-readable string that represents a doc-comment.
     */
    val documentation: String? = null,

    /**
     * A string that should be used when comparing this item with other items.
     *
     * When `null` the label is used.
     */
    val sortText: String? = null,

    /**
     * A string that should be used when filtering a set of completion items.
     *
     * When `null` the label is used.
     */
    val filterText: String? = null,

    /**
     * A string that should be inserted a document when selecting this
     * completion.
     *
     * When `null` the label is used.
     */
    val insertText: String? = null,

    /**
     * An edit which is applied to a document when selecting this completion.
     *
     * When an edit is provided the value of insertText is ignored.
     */
    val textEdit: TextEdit? = null,

    /**
     * A data entry field that is preserved on a completion item between
     * a completion and a completion resolve request.
     */
    val data: JsonElement? = null
) {
    companion object : JsonSerialization<CompletionItem> {
        override fun serializeToJson(value: CompletionItem): JsonObject = buildJsonObject {
            put("label", value.label, JsonString)
            putOptional("kind", value.kind, CompletionItemKind)
            putOptional("detail", value.detail, JsonString)
            putOptional("documentation", value.documentation, JsonString)
            putOptional("sortText", value.sortText, JsonString)
            putOptional("filterText", value.filterText, JsonString)
            putOptional("insertText", value.insertText, JsonString)
            putOptional("textEdit", value.textEdit, TextEdit)
            putOptional("data", value.data, LSPAny)
        }

        override fun deserialize(json: JsonElement): CompletionItem = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> CompletionItem(
                label = json.get("label", JsonString),
                kind = json.getOptional("kind", CompletionItemKind),
                detail = json.getOptional("detail", JsonString),
                documentation = json.getOptional("documentation", JsonString),
                sortText = json.getOptional("sortText", JsonString),
                filterText = json.getOptional("filterText", JsonString),
                insertText = json.getOptional("insertText", JsonString),
                textEdit = json.getOptional("textEdit", TextEdit),
                data = json.getOptional("data", LSPAny)
            )
        }
    }
}

/**
 * The completion entry kind.
 *
 * @param kind the completion entry kind.
 *
 * @since 1.0.0
 */
@JvmInline
value class CompletionItemKind(val kind: Int) {
    companion object : JsonSerialization<CompletionItemKind> {
        override fun serializeToJson(value: CompletionItemKind): JsonPrimitive = JsonPrimitive(value.kind)

        override fun deserialize(json: JsonElement): CompletionItemKind {
            return CompletionItemKind(JsonInt.deserialize(json))
        }

        /**
         * A text completion entry.
         */
        val Text: CompletionItemKind = CompletionItemKind(1)

        /**
         * A method completion entry.
         */
        val Method: CompletionItemKind = CompletionItemKind(2)

        /**
         * A function completion entry.
         */
        val Function: CompletionItemKind = CompletionItemKind(3)

        /**
         * A constructor completion entry.
         */
        val Constructor: CompletionItemKind = CompletionItemKind(4)

        /**
         * A field completion entry.
         */
        val Field: CompletionItemKind = CompletionItemKind(5)

        /**
         * A variable completion entry.
         */
        val Variable: CompletionItemKind = CompletionItemKind(6)

        /**
         * A class completion entry.
         */
        val Class: CompletionItemKind = CompletionItemKind(7)

        /**
         * An interface completion entry.
         */
        val Interface: CompletionItemKind = CompletionItemKind(8)

        /**
         * A module completion entry.
         */
        val Module: CompletionItemKind = CompletionItemKind(9)

        /**
         * A property completion entry.
         */
        val Property: CompletionItemKind = CompletionItemKind(10)

        /**
         * A unit completion entry.
         */
        val Unit: CompletionItemKind = CompletionItemKind(11)

        /**
         * A value completion entry.
         */
        val Value: CompletionItemKind = CompletionItemKind(12)

        /**
         * An enumeration completion entry.
         */
        val Enum: CompletionItemKind = CompletionItemKind(13)

        /**
         * A keyword completion entry.
         */
        val Keyword: CompletionItemKind = CompletionItemKind(14)

        /**
         * A snippet completion entry.
         */
        val Snippet: CompletionItemKind = CompletionItemKind(15)

        /**
         * A color completion entry.
         */
        val Color: CompletionItemKind = CompletionItemKind(16)

        /**
         * A file completion entry.
         */
        val File: CompletionItemKind = CompletionItemKind(17)

        /**
         * A reference completion entry.
         */
        val Reference: CompletionItemKind = CompletionItemKind(18)
    }
}

private val CompletionItemArray = JsonTypedArray(CompletionItem)

/**
 * The response of a completion request.
 *
 * @param id the request id
 * @param result the result of the request
 * @param error the error object in case the request failed
 * @param jsonrpc the version of the JSON-RPC protocol
 *
 * @since 1.0.0
 */
data class CompletionResponse(
    override val id: JsonIntOrString?,
    override val result: List<CompletionItem>,
    override val error: TypedErrorObject<JsonElement>?,
    override val jsonrpc: String
) : TypedResponseObject<List<CompletionItem>, JsonElement> {
    companion object : TypedResponseObjectConverter<List<CompletionItem>, JsonElement> {
        override fun convert(response: ResponseObject): TypedResponseObject<List<CompletionItem>, JsonElement> {
            return CompletionResponse(
                id = response.id,
                result = response.result?.let { CompletionItemArray.deserialize(it) } ?: listOf(),
                error = response.error,
                jsonrpc = response.jsonrpc
            )
        }
    }
}

/**
 * The completion request is sent from the client to the server to compute completion items
 * at a given cursor position.
 *
 * If computing complete completion items is expensive, servers can additionally provide a
 * handler for the resolve completion item request. This request is sent when a completion
 * item is selected in the user interface.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @return an initialize result response
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.completion(
    handler: TextDocumentPositionParams.() -> List<CompletionItem>
): Unit = request.method(
    method = TextDocumentRequest.COMPLETION,
    handler = handler,
    paramsSerializer = TextDocumentPositionParams,
    resultSerializer = CompletionItemArray
)

/**
 * The completion request is sent from the client to the server to compute completion items
 * at a given cursor position.
 *
 * If computing complete completion items is expensive, servers can additionally provide a
 * handler for the resolve completion item request. This request is sent when a completion
 * item is selected in the user interface.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param params the request parameters
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.completion(
    params: TextDocumentPositionParams,
    responseHandler: (TypedResponseObject<List<CompletionItem>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = server.sendRequest(
    method = TextDocumentRequest.COMPLETION,
    params = TextDocumentPositionParams.serializeToJson(params),
    responseHandler = responseHandler,
    responseObjectConverter = CompletionResponse
)

/**
 * The completion request is sent from the client to the server to compute completion items
 * at a given cursor position.
 *
 * If computing complete completion items is expensive, servers can additionally provide a
 * handler for the resolve completion item request. This request is sent when a completion
 * item is selected in the user interface.
 *
 * __NOTE:__ In LSP 1.x, the `textDocument` parameter was an inlined `uri` parameter.
 *
 * @param textDocument the text document
 * @param position the position inside the text document
 * @param responseHandler the callback to process the response for the request
 * @return the ID of the request
 *
 * @since 2.0.0
 */
fun TextDocumentJsonRpcServer.completion(
    textDocument: TextDocumentIdentifier,
    position: Position,
    responseHandler: (TypedResponseObject<List<CompletionItem>, JsonElement>.() -> Unit)? = null
): JsonIntOrString = completion(
    params = TextDocumentPositionParams(
        textDocument = textDocument,
        position = position
    ),
    responseHandler = responseHandler
)
