package com.example.websocketexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    private val socketListener: EchoWebSocketListener
) : ViewModel() {

    private val _socketStatus = MutableLiveData<Boolean>()
    val socketStatus: LiveData<Boolean> = _socketStatus

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
    }

    fun addMessage(message: String) = viewModelScope.launch(Dispatchers.Main) {
        _message.value = message
    }
}
