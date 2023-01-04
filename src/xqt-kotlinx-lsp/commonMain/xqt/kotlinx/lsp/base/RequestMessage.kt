// Copyright (C) 2023 Reece H. Dunn. SPDX-License-Identifier: Apache-2.0
package xqt.kotlinx.lsp.base

import xqt.kotlinx.rpc.json.protocol.RequestObject

/**
 * A request message to describe a request between the client and the server.
 *
 * Every processed request must send a response back to the sender of the request.
 *
 * @since 1.0.0
 */
typealias RequestMessage = RequestObject
