package com.example.websocketexample.data

import com.example.websocketexample.data.remote.ChatWebSocketClient
import com.example.websocketexample.data.remote.WebSocketEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val webSocketClient: ChatWebSocketClient
) {
//    fun observeWebSocketEvents(): Flow<WebSocketEvent> {
//        return webSocketClient.observe()
//    }

    fun observeWebSocketEvents(): Flow<WebSocketEvent> {

        throw Exception("Test")
        return flow {
            while (true) {
                println("Flow is running")
                emit(WebSocketEvent.WebSocketStatus(true))
                delay(1000)
                emit(WebSocketEvent.WebSocketStatus(false))
                delay(1000)
                emit(WebSocketEvent.WebSocketStatus(true))
            }
        }
    }

    suspend fun setupWebSocketService() {
        webSocketClient.setupWebSocketService()
    }

    fun sendMessage(message: String) {
        webSocketClient.sendMessage(message)
    }
}
