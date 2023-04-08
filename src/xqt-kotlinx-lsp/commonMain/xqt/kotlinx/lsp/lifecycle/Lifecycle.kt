// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.lifecycle

/**
 * A lifecycle request in the default namespace.
 *
 * @since 1.0.0
 */
object LifecycleRequest {
    /**
     * The initialize request is sent as the first request from the client to the server.
     *
     * If the server receives request or notification before the `initialize` request it
     * should act as follows:
     * * for a request the response should be errored with `code: -32001`.
     *   The message can be picked by the server.
     * * notifications should be dropped.
     */
    const val INITIALIZE: String = "initialize"

    /**
     * Asks the server to shut down, but to not exit.
     *
     * The shutdown request is sent from the client to the server. The server must
     * not exit, otherwise the response might not be delivered correctly to the
     * client.
     *
     * There is a separate exit notification that asks the server to exit.
     */
    const val SHUTDOWN: String = "shutdown"
}

/**
 * A lifecycle notification in the default namespace.
 *
 * @since 1.0.0
 */
object LifecycleNotification {
    /**
     * A notification to ask the server to exit its process.
     *
     * The server should exit with `success` code 0 if the shutdown request has been received
     * before; otherwise with `error` code 1.
     */
    const val EXIT: String = "exit"
}
