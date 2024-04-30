package com.example.websocketexample.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import kotlin.coroutines.EmptyCoroutineContext

class ChatWebSocketClient(
    private val client: OkHttpClient,
    private val request: Request
) {

    private var webSocket: WebSocket? = null

    // TODO точно ли нужен sharedFlow
    private val sharedFlow = MutableSharedFlow<WebSocketEvent>()
    private val scope = CoroutineScope(EmptyCoroutineContext)

    suspend fun setupWebSocketService() {
        webSocket = client.newWebSocket(request, SocketListener(
            payload = { event ->
                scope.launch {
                    sharedFlow.emit(event)
                }
            }
        ))
    }

    fun observe() = sharedFlow

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }
}
