package com.example.websocketexample.data

import com.example.websocketexample.data.remote.ChatWebSocketClient
import com.example.websocketexample.data.remote.WebSocketEvent
import kotlinx.coroutines.flow.Flow

class Repository(
    private val webSocketClient: ChatWebSocketClient
) {
    fun observeWebSocketEvents(): Flow<WebSocketEvent> {
        return webSocketClient.observe()
    }

    suspend fun setupWebSocketService() {
        webSocketClient.setupWebSocketService()
    }

    fun sendMessage(message: String) {
        webSocketClient.sendMessage(message)
    }
}
