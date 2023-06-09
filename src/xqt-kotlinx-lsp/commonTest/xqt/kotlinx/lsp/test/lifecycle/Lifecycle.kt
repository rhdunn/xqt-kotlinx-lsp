// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.test.lifecycle

import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import xqt.kotlinx.lsp.base.ErrorCodes
import xqt.kotlinx.lsp.base.InternalError
import xqt.kotlinx.lsp.lifecycle.*
import xqt.kotlinx.lsp.test.base.testJsonRpc
import xqt.kotlinx.lsp.textDocument.TextDocumentSyncKind
import xqt.kotlinx.rpc.json.protocol.jsonRpc
import xqt.kotlinx.rpc.json.protocol.notification
import xqt.kotlinx.rpc.json.protocol.request
import xqt.kotlinx.rpc.json.serialization.jsonObjectOf
import xqt.kotlinx.rpc.json.serialization.types.JsonIntOrString
import xqt.kotlinx.rpc.json.serialization.types.JsonProperty
import xqt.kotlinx.test.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("DEPRECATION")
@DisplayName("Lifecycle DSL")
class LifecycleDSL {
    // region initialize request

    @Test
    @DisplayName("supports initialize requests")
    fun supports_initialize_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                initialize {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("initialize", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals(1234, processId)
                    assertEquals(JsonProperty<String>(null), rootPath)
                    assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), capabilities)

                    InitializeResult(
                        capabilities = ServerCapabilities(
                            textDocumentSync = TextDocumentSyncKind.Full
                        )
                    )
                }
            }
        }

        assertEquals(true, called, "The initialize DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to jsonObjectOf(
                    "capabilities" to jsonObjectOf(
                        "textDocumentSync" to JsonPrimitive(1)
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports initialize requests throwing an InitializeError")
    fun supports_initialize_requests_throwing_an_initialize_error() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                initialize {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("initialize", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)

                    assertEquals(1234, processId)
                    assertEquals(JsonProperty<String>(null), rootPath)
                    assertEquals(ClientCapabilities("test" to JsonPrimitive("lorem ipsum")), capabilities)

                    throw InitializeError(message = "Lorem ipsum", retry = true)
                }
            }
        }

        assertEquals(true, called, "The initialize DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "error" to jsonObjectOf(
                    "code" to JsonPrimitive(ErrorCodes.InternalError.code),
                    "message" to JsonPrimitive("Lorem ipsum"),
                    "data" to jsonObjectOf(
                        "retry" to JsonPrimitive(true)
                    )
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports initialize requests reporting InvalidParams errors")
    fun supports_initialize_requests_reporting_invalid_params_errors() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(false),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            )
        )

        var called = false
        server.jsonRpc {
            request {
                initialize {
                    called = true
                    throw InitializeError(message = "Should not be called")
                }
            }
        }

        assertEquals(false, called, "The initialize DSL should not have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "error" to jsonObjectOf(
                    "code" to JsonPrimitive(ErrorCodes.InvalidParams.code),
                    "message" to JsonPrimitive("Unsupported kind type 'boolean'")
                )
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending initialize requests using parameter objects")
    fun supports_sending_initialize_requests_using_parameter_objects() = testJsonRpc {
        val id = client.initialize(
            params = InitializeParams(
                processId = 1234,
                rootPath = JsonProperty(null),
                capabilities = ClientCapabilities(
                    "test" to JsonPrimitive("lorem ipsum")
                )
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports initialize request callback receiving a result from parameter objects")
    fun supports_initialize_request_callback_receiving_a_result_from_parameter_objects() = testJsonRpc {
        var called = 0

        client.initialize(
            params = InitializeParams(
                processId = 1234,
                capabilities = ClientCapabilities(
                    "test" to JsonPrimitive("lorem ipsum")
                )
            )
        ) {
            ++called

            assertEquals(
                ServerCapabilities(
                    textDocumentSync = TextDocumentSyncKind.Full
                ),
                result?.capabilities
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                initialize {
                    InitializeResult(
                        capabilities = ServerCapabilities(
                            textDocumentSync = TextDocumentSyncKind.Full
                        )
                    )
                }
            }
        }

        assertEquals(0, called, "The initialize DSL handler should not have been called.")
        client.jsonRpc {} // The "initialize" response is processed by the handler callback.
        assertEquals(1, called, "The initialize DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports initialize request callback receiving an error from parameter objects")
    fun supports_initialize_request_callback_receiving_an_error_from_parameter_objects() = testJsonRpc {
        var called = 0

        client.initialize(
            params = InitializeParams(
                processId = 1234,
                capabilities = ClientCapabilities(
                    "test" to JsonPrimitive("lorem ipsum")
                )
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
            assertEquals(InitializeError(retry = true), error?.data)
        }

        server.jsonRpc {
            request {
                initialize {
                    throw InitializeError(message = "Lorem ipsum", retry = true)
                }
            }
        }

        assertEquals(0, called, "The initialize DSL handler should not have been called.")
        client.jsonRpc {} // The "initialize" response is processed by the handler callback.
        assertEquals(1, called, "The initialize DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending initialize requests using function parameters")
    fun supports_sending_initialize_requests_using_function_parameters() = testJsonRpc {
        val id = client.initialize(
            processId = 1234,
            rootPath = JsonProperty(null),
            capabilities = ClientCapabilities(
                "test" to JsonPrimitive("lorem ipsum")
            )
        )
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("initialize"),
                "id" to JsonPrimitive(1),
                "params" to jsonObjectOf(
                    "processId" to JsonPrimitive(1234),
                    "rootPath" to JsonNull,
                    "capabilities" to jsonObjectOf(
                        "test" to JsonPrimitive("lorem ipsum")
                    )
                )
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports initialize request callback receiving a result using function parameters")
    fun supports_initialize_request_callback_receiving_a_result_using_function_parameters() = testJsonRpc {
        var called = 0

        client.initialize(
            processId = 1234,
            capabilities = ClientCapabilities(
                "test" to JsonPrimitive("lorem ipsum")
            )
        ) {
            ++called

            assertEquals(
                ServerCapabilities(
                    textDocumentSync = TextDocumentSyncKind.Full
                ),
                result?.capabilities
            )

            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                initialize {
                    InitializeResult(
                        capabilities = ServerCapabilities(
                            textDocumentSync = TextDocumentSyncKind.Full
                        )
                    )
                }
            }
        }

        assertEquals(0, called, "The initialize DSL handler should not have been called.")
        client.jsonRpc {} // The "initialize" response is processed by the handler callback.
        assertEquals(1, called, "The initialize DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports initialize request callback receiving an error using function parameters")
    fun supports_initialize_request_callback_receiving_an_error_using_function_parameters() = testJsonRpc {
        var called = 0

        client.initialize(
            processId = 1234,
            capabilities = ClientCapabilities(
                "test" to JsonPrimitive("lorem ipsum")
            )
        ) {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
            assertEquals(InitializeError(retry = true), error?.data)
        }

        server.jsonRpc {
            request {
                initialize {
                    throw InitializeError(message = "Lorem ipsum", retry = true)
                }
            }
        }

        assertEquals(0, called, "The initialize DSL handler should not have been called.")
        client.jsonRpc {} // The "initialize" response is processed by the handler callback.
        assertEquals(1, called, "The initialize DSL handler should have been called.")
    }

    // endregion
    // region shutdown request

    @Test
    @DisplayName("supports shutdown requests")
    fun supports_shutdown_requests() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("shutdown"),
                "id" to JsonPrimitive(1)
            )
        )

        var called = false
        server.jsonRpc {
            request {
                shutdown {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("shutdown", method)
                    assertEquals(JsonIntOrString.IntegerValue(1), id)
                }
            }
        }

        assertEquals(true, called, "The shutdown DSL should have been called.")

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "id" to JsonPrimitive(1),
                "result" to JsonNull
            ),
            client.receive()
        )
    }

    @Test
    @DisplayName("supports sending shutdown requests")
    fun supports_sending_shutdown_requests() = testJsonRpc {
        val id = client.shutdown()
        assertEquals(JsonIntOrString.IntegerValue(1), id)

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("shutdown"),
                "id" to JsonPrimitive(1)
            ),
            server.receive()
        )
    }

    @Test
    @DisplayName("supports sending shutdown requests receiving null responses")
    fun supports_sending_shutdown_requests_receiving_null_responses() = testJsonRpc {
        var called = 0

        client.shutdown {
            ++called

            assertEquals(JsonNull, result)
            assertEquals(null, error)
        }

        server.jsonRpc {
            request {
                shutdown {
                }
            }
        }

        assertEquals(0, called, "The shutdown DSL handler should not have been called.")
        client.jsonRpc {} // The "shutdown" response is processed by the handler callback.
        assertEquals(1, called, "The shutdown DSL handler should have been called.")
    }

    @Test
    @DisplayName("supports sending shutdown requests receiving error responses")
    fun supports_sending_shutdown_requests_receiving_error_responses() = testJsonRpc {
        var called = 0

        client.shutdown {
            ++called

            assertEquals(null, result)

            assertEquals(ErrorCodes.InternalError, error?.code)
            assertEquals("Lorem ipsum", error?.message)
        }

        server.jsonRpc {
            request {
                shutdown {
                    throw InternalError(message = "Lorem ipsum")
                }
            }
        }

        assertEquals(0, called, "The shutdown DSL handler should not have been called.")
        client.jsonRpc {} // The "shutdown" response is processed by the handler callback.
        assertEquals(1, called, "The shutdown DSL handler should have been called.")
    }

    // endregion
    // region exit notification

    @Test
    @DisplayName("supports exit notifications")
    fun supports_exit_notifications() = testJsonRpc {
        client.send(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("exit")
            )
        )

        var called = false
        server.jsonRpc {
            notification {
                exit {
                    called = true

                    assertEquals("2.0", jsonrpc)
                    assertEquals("exit", method)
                }
            }
        }

        assertEquals(true, called, "The exit DSL should have been called.")
    }

    @Test
    @DisplayName("supports sending exit notifications")
    fun supports_sending_exit_notifications() = testJsonRpc {
        client.exit()

        assertEquals(
            jsonObjectOf(
                "jsonrpc" to JsonPrimitive("2.0"),
                "method" to JsonPrimitive("exit")
            ),
            server.receive()
        )
    }

    // endregion
}
