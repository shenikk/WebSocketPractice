package com.example.websocketexample.data

import com.example.websocketexample.ChatViewModel
import kotlinx.coroutines.CoroutineScope
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlin.coroutines.EmptyCoroutineContext

class EchoWebSocketListener(
//    private val socketListener: SocketListener
//    private val chatViewModel: ChatViewModel
) : WebSocketListener() {

    private val scope = CoroutineScope(EmptyCoroutineContext)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("EchoWebSocketListener onOpen")
//        chatViewModel.setStatus(true)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("EchoWebSocketListener onMessage $text")
//        chatViewModel.addMessage(text)
//        scope.launch(Dispatchers.Main) {
//            socketListener.onMessage(text)
//        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("EchoWebSocketListener onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("EchoWebSocketListener onClosed")
//        chatViewModel.setStatus(false)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)

        println("EchoWebSocketListener onFailure: $t, $response")
    }
}

interface SocketListener {
    fun onMessage(message: String)
}
