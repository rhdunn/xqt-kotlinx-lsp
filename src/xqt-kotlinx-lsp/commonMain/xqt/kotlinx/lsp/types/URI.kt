// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.types

import xqt.kotlinx.rpc.json.serialization.StringSerialization
import xqt.kotlinx.rpc.json.uri.Authority
import xqt.kotlinx.rpc.json.uri.Uri
import xqt.kotlinx.rpc.json.uri.UriScheme
import kotlin.jvm.JvmInline

/**
 * The URI of a document.
 *
 * Many of the interfaces contain fields that correspond to the URI of a document.
 * For clarity, the type of such a field is declared as a `DocumentUri`. Over the
 * wire, it will still be transferred as a string, but this guarantees that the
 * contents of that string can be parsed as a valid URI.
 *
 * @param uri the uri of the document
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc3986">RFC 3986 Uniform Resource Identifier (URI): Generic Syntax</a>
 * @since 3.0.0
 */
@JvmInline
value class DocumentUri(val uri: Uri) {
    /**
     * The URI scheme.
     *
     * @see <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.1">RFC 3986 (3.1) Scheme</a>
     */
    val scheme: UriScheme
        get() = uri.scheme

    /**
     * The URI authority.
     *
     * @see <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.2">RFC 3986 (3.2) Authority</a>
     */
    val authority: Authority?
        get() = uri.authority

    /**
     * The URI path.
     *
     * @see <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.3">RFC 3986 (3.3) Path</a>
     */
    val path: String
        get() = uri.path

    /**
     * The URI query.
     *
     * @see <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.4">RFC 3986 (3.4) Query</a>
     */
    val queryString: String?
        get() = uri.queryString

    /**
     * The URI fragment.
     *
     * @see <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.5">RFC 3986 (3.5) Fragment</a>
     */
    val fragment: String?
        get() = uri.fragment

    override fun toString(): String = uri.toString()

    companion object : StringSerialization<DocumentUri> {
        override fun deserialize(value: String): DocumentUri {
            return DocumentUri(Uri.deserialize(value))
        }
    }
}

/**
 * The URI of a document.
 *
 * Many of the interfaces contain fields that correspond to the URI of a document.
 * For clarity, the type of such a field is declared as a `DocumentUri`. Over the
 * wire, it will still be transferred as a string, but this guarantees that the
 * contents of that string can be parsed as a valid URI.
 *
 * @see <a href="https://www.rfc-editor.org/rfc/rfc3986">RFC 3986 Uniform Resource Identifier (URI): Generic Syntax</a>
 * @since 3.0.0
 */
fun DocumentUri(value: String): DocumentUri = DocumentUri.deserialize(value)
