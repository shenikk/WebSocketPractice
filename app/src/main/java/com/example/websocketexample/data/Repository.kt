package com.example.websocketexample.data

import com.example.websocketexample.data.remote.ChatWebSocketClient
import com.example.websocketexample.data.remote.WebSocketEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val webSocketClient: ChatWebSocketClient
) {
    fun observeWebSocketEvents(): Flow<WebSocketEvent> {
        return webSocketClient.observe()
    }

// Method for test and study purposes
//    fun observeWebSocketEvents(): Flow<WebSocketEvent> {
//        return flow {
////            while (true) {
//                println("Flow is running")
//                emit(WebSocketEvent.WebSocketStatus(isOpen = false))
//                println("Flow 1")
//                delay(1000)
//                emit(WebSocketEvent.WebSocketStatus(isOpen = false))
//                println("Flow 2")
//                delay(1000)
//                emit(WebSocketEvent.WebSocketStatus(isOpen = false))
//                println("Flow 3")
////            }
//        }
//    }

    suspend fun setupWebSocketService() {
        webSocketClient.setupWebSocketService()
    }

    fun sendMessage(message: String) {
        webSocketClient.sendMessage(message)
    }
}
