package com.example.websocketexample.data.remote

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SocketListener(
    private val payload: (WebSocketEvent) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("EchoWebSocketListener onOpen")
        payload.invoke(WebSocketEvent.WebSocketStatus(isOpen = true))
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("EchoWebSocketListener onMessage $text")
        payload.invoke(WebSocketEvent.WebSocketMessage(message = text))
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("EchoWebSocketListener onClosed")
        payload.invoke(WebSocketEvent.WebSocketStatus(isOpen = false))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println("EchoWebSocketListener onFailure: $t, $response")
        payload.invoke(WebSocketEvent.WebSocketError(error = t))
    }
}
