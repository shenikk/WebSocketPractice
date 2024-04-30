package com.example.websocketexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.websocketexample.data.remote.ChatWebSocketClient
import com.example.websocketexample.data.Repository
import com.example.websocketexample.data.remote.WebSocketEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val state: StateFlow<ScreenUiState> = repository.observeWebSocketEvents()
        .map { webSocketEvent ->
            chatMapper.toUiEvent(webSocketEvent)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ScreenUiState.Loading
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
