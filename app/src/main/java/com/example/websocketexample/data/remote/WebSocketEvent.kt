package com.example.websocketexample.data.remote

import okhttp3.WebSocket

sealed class WebSocketEvent {
    data class WebSocketStatus(val isOpen: Boolean) : WebSocketEvent()

    data class WebSocketError(val error: Throwable) : WebSocketEvent()

    data class WebSocketMessage(val message: String) : WebSocketEvent()
}
