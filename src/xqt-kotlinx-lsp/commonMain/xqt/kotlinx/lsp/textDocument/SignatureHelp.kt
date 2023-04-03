// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.textDocument

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import xqt.kotlinx.lsp.base.UInteger
import xqt.kotlinx.lsp.types.TextDocumentPosition
import xqt.kotlinx.rpc.json.protocol.params
import xqt.kotlinx.rpc.json.protocol.sendResult
import xqt.kotlinx.rpc.json.serialization.*
import xqt.kotlinx.rpc.json.serialization.types.JsonString
import xqt.kotlinx.rpc.json.serialization.types.JsonTypedArray

/**
 * Signature help options.
 *
 * @since 1.0.0
 */
data class SignatureHelpOptions(
    /**
     * The characters that trigger signature help automatically.
     */
    val triggerCharacters: List<String> = emptyList()
) {
    companion object : JsonSerialization<SignatureHelpOptions> {
        private val JsonStringArray = JsonTypedArray(JsonString)

        override fun serializeToJson(value: SignatureHelpOptions): JsonObject = buildJsonObject {
            putOptional("triggerCharacters", value.triggerCharacters, JsonStringArray)
        }

        override fun deserialize(json: JsonElement): SignatureHelpOptions = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> SignatureHelpOptions(
                triggerCharacters = json.getOptional("triggerCharacters", JsonStringArray)
            )
        }
    }
}

/**
 * Signature help represents the signature of something callable.
 *
 * There can be multiple signatures, but only one active and only one active
 * parameter.
 *
 * @since 1.0.0
 */
data class SignatureHelp(
    /**
     * One or more signatures.
     */
    val signatures: List<SignatureInformation>,

    /**
     * The active signature.
     */
    val activeSignature: UInt? = null,

    /**
     * The active parameter of the active signature.
     */
    val activeParameter: UInt? = null
) {
    companion object : JsonSerialization<SignatureHelp> {
        private val SignatureInformationArray = JsonTypedArray(SignatureInformation)

        override fun serializeToJson(value: SignatureHelp): JsonObject = buildJsonObject {
            put("signatures", value.signatures, SignatureInformationArray)
            putOptional("activeSignature", value.activeSignature, UInteger)
            putOptional("activeParameter", value.activeParameter, UInteger)
        }

        override fun deserialize(json: JsonElement): SignatureHelp = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> SignatureHelp(
                signatures = json.get("signatures", SignatureInformationArray),
                activeSignature = json.getOptional("activeSignature", UInteger),
                activeParameter = json.getOptional("activeParameter", UInteger)
            )
        }
    }
}

/**
 * Represents the signature of something callable.
 *
 * @since 1.0.0
 */
data class SignatureInformation(
    /**
     * The label of this signature.
     *
     * Will be shown in the UI.
     */
    val label: String,

    /**
     * The human-readable doc-comment of this signature.
     *
     * Will be shown in the UI but can be omitted.
     */
    val documentation: String? = null,

    /**
     * The parameters of this signature.
     */
    val parameters: List<ParameterInformation>
) {
    companion object : JsonSerialization<SignatureInformation> {
        private val ParameterInformationArray = JsonTypedArray(ParameterInformation)

        override fun serializeToJson(value: SignatureInformation): JsonObject = buildJsonObject {
            put("label", value.label, JsonString)
            putOptional("documentation", value.documentation, JsonString)
            put("parameters", value.parameters, ParameterInformationArray)
        }

        override fun deserialize(json: JsonElement): SignatureInformation = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> SignatureInformation(
                label = json.get("label", JsonString),
                documentation = json.getOptional("documentation", JsonString),
                parameters = json.get("parameters", ParameterInformationArray)
            )
        }
    }
}

/**
 * Represents a parameter of a callable-signature.
 *
 * @since 1.0.0
 */
data class ParameterInformation(
    /**
     * The label of this signature.
     *
     * Will be shown in the UI.
     */
    val label: String,

    /**
     * The human-readable doc-comment of this signature.
     *
     * Will be shown in the UI but can be omitted.
     */
    val documentation: String? = null
) {
    companion object : JsonSerialization<ParameterInformation> {
        override fun serializeToJson(value: ParameterInformation): JsonObject = buildJsonObject {
            put("label", value.label, JsonString)
            putOptional("documentation", value.documentation, JsonString)
        }

        override fun deserialize(json: JsonElement): ParameterInformation = when (json) {
            !is JsonObject -> unsupportedKindType(json)
            else -> ParameterInformation(
                label = json.get("label", JsonString),
                documentation = json.getOptional("documentation", JsonString),
            )
        }
    }
}

/**
 * The signature help request is sent from the client to the server to request signature
 * information at a given cursor position.
 *
 * @since 1.0.0
 */
fun TextDocumentRequest.signatureHelp(handler: TextDocumentPosition.() -> SignatureHelp) {
    if (request.method == TextDocumentRequest.SIGNATURE_HELP) {
        val result = request.params(TextDocumentPosition).handler()
        request.sendResult(result, SignatureHelp)
    }
}
