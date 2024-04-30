package com.example.websocketexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.websocketexample.data.Repository
import com.example.websocketexample.data.remote.ChatWebSocketClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class ChatViewModel : ViewModel() {

    // TODO унести все в DI
    private val client = OkHttpClient()
    private val request = Request.Builder()
        .url("wss://websocket-echo.glitch.me")
        .build()
    private val webSocketClient = ChatWebSocketClient(client, request)
    private val repository: Repository = Repository(webSocketClient)
    private val chatMapper = ChatMapper()

    val state: Flow<ScreenUiState> = repository.observeWebSocketEvents()
        .map { webSocketEvent ->
            chatMapper.toUiEvent(webSocketEvent)
        }
        .onStart {
            emit(ScreenUiState.Loading)
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun setUpWebSocketConnection() {
        viewModelScope.launch {
            repository.setupWebSocketService()
        }
    }

    fun sendMessage(message: String, payload: () -> Unit) {
        viewModelScope.launch {
            repository.sendMessage(message)
            payload.invoke()
        }
    }
}

sealed interface ScreenUiState {
    data object Loading : ScreenUiState
    data object Error : ScreenUiState
    data class Ready(val message: String? = null) : ScreenUiState
}
