package com.example.websocketexample.presentation

import com.example.websocketexample.data.remote.WebSocketEvent
import com.example.websocketexample.data.remote.WebSocketEvent.WebSocketMessage
import com.example.websocketexample.data.remote.WebSocketEvent.WebSocketError
import com.example.websocketexample.data.remote.WebSocketEvent.WebSocketStatus

class ChatMapper {

    fun toUiEvent(event: WebSocketEvent): ScreenUiState {
        return when (event) {
            is WebSocketMessage -> ScreenUiState.Ready(event.message)
            is WebSocketError -> ScreenUiState.Error
            is WebSocketStatus -> {
                if (event.isOpen) {
                    ScreenUiState.Ready()
                } else {
                    ScreenUiState.Loading
                }
            }
        }
    }
}
