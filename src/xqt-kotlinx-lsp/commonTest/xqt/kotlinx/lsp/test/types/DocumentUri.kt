// Copyright (C) 2022 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.types

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.types.DocumentUri
import xqt.kotlinx.rpc.json.serialization.UnsupportedKindTypeException
import xqt.kotlinx.rpc.json.serialization.jsonArrayOf
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.uri.Authority
import xqt.kotlinx.rpc.json.uri.UriScheme
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@DisplayName("The DocumentUri type")
class TheDocumentUriType {
    @Test
    @DisplayName("supports the 'urn' URI scheme")
    fun supports_the_urn_uri_scheme() {
        val uri = DocumentUri.deserialize("urn:lorem:ipsum:dolor")
        assertEquals(UriScheme.URN, uri.scheme)
        assertEquals(null, uri.authority)
        assertEquals("lorem:ipsum:dolor", uri.path)
        assertEquals(null, uri.queryString)
        assertEquals(null, uri.fragment)

        assertEquals("urn:lorem:ipsum:dolor", DocumentUri.serializeToString(uri))
        assertEquals("urn:lorem:ipsum:dolor", uri.toString())
    }

    @Test
    @DisplayName("supports the 'file' URI scheme")
    fun supports_the_file_uri_scheme() {
        val uri = DocumentUri.deserialize("file:///lorem/ipsum/dolor")
        assertEquals(UriScheme.File, uri.scheme)
        assertEquals(Authority(host = ""), uri.authority)
        assertEquals("/lorem/ipsum/dolor", uri.path)
        assertEquals(null, uri.queryString)
        assertEquals(null, uri.fragment)

        assertEquals("file:///lorem/ipsum/dolor", DocumentUri.serializeToString(uri))
        assertEquals("file:///lorem/ipsum/dolor", uri.toString())
    }

    @Test
    @DisplayName("supports the 'http' URI scheme")
    fun supports_the_http_uri_scheme() {
        val uri = DocumentUri.deserialize("http://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum")
        assertEquals(UriScheme.HTTP, uri.scheme)
        assertEquals(Authority(host = "localhost"), uri.authority)
        assertEquals("/lorem/ipsum/dolor", uri.path)
        assertEquals("key1=alpha&key2=beta", uri.queryString)
        assertEquals("lorem-ipsum", uri.fragment)

        assertEquals(
            "http://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum",
            DocumentUri.serializeToString(uri)
        )
        assertEquals("http://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum", uri.toString())
    }

    @Test
    @DisplayName("supports the 'https' URI scheme")
    fun supports_the_https_uri_scheme() {
        val uri = DocumentUri.deserialize("https://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum")
        assertEquals(UriScheme.HTTPS, uri.scheme)
        assertEquals(Authority(host = "localhost"), uri.authority)
        assertEquals("/lorem/ipsum/dolor", uri.path)
        assertEquals("key1=alpha&key2=beta", uri.queryString)
        assertEquals("lorem-ipsum", uri.fragment)

        assertEquals(
            "https://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum",
            DocumentUri.serializeToString(uri)
        )
        assertEquals("https://localhost/lorem/ipsum/dolor?key1=alpha&key2=beta#lorem-ipsum", uri.toString())
    }

    @Test
    @DisplayName("throws an error if the kind type is not supported")
    fun throws_an_error_if_the_kind_type_is_not_supported() {
        val e1 = assertFails { DocumentUri.deserialize(jsonObjectOf()) }
        assertEquals(UnsupportedKindTypeException::class, e1::class)
        assertEquals("Unsupported kind type 'object'", e1.message)

        val e2 = assertFails { DocumentUri.deserialize(jsonArrayOf()) }
        assertEquals(UnsupportedKindTypeException::class, e2::class)
        assertEquals("Unsupported kind type 'array'", e2.message)

        val e3 = assertFails { DocumentUri.deserialize(JsonNull) }
        assertEquals(UnsupportedKindTypeException::class, e3::class)
        assertEquals("Unsupported kind type 'null'", e3.message)

        val e4 = assertFails { DocumentUri.deserialize(JsonPrimitive(true)) }
        assertEquals(UnsupportedKindTypeException::class, e4::class)
        assertEquals("Unsupported kind type 'boolean'", e4.message)

        val e5 = assertFails { DocumentUri.deserialize(JsonPrimitive(1)) }
        assertEquals(UnsupportedKindTypeException::class, e5::class)
        assertEquals("Unsupported kind type 'integer'", e5.message)

        val e6 = assertFails { DocumentUri.deserialize(JsonPrimitive(1.2)) }
        assertEquals(UnsupportedKindTypeException::class, e6::class)
        assertEquals("Unsupported kind type 'decimal'", e6.message)
    }
}
