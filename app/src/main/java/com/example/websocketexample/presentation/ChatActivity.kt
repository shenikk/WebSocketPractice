package com.example.websocketexample.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.websocketexample.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChatMessageAdapter
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setUpViewModel()
        setupRecyclerViewMessage()
        setUpEditTextView()
    }

    private fun setupRecyclerViewMessage() {
        adapter = ChatMessageAdapter()
        binding.recyclerMessage.apply {
            adapter = this@ChatActivity.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        viewModel.setUpWebSocketConnection()


//        repeatOnLifecycle vs flowWithLifecycle
        //https://bladecoder.medium.com/kotlins-flow-in-viewmodels-it-s-complicated-556b472e281a
//        this.lifecycleScope.launch {
//            viewModel.state.flowWithLifecycle(viewLifecycleOwner.l) { data ->
//                displayResult(data)
//            }
//        }

        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle)
                .collect { data ->
                    displayResult(data)
                }
        }
    }

    private fun displayResult(data: ScreenUiState) {
        when (data) {
            is ScreenUiState.Loading -> {
                println(" EchoWebSocketListener Loading")
                binding.loader.visibility = View.VISIBLE
            }

            is ScreenUiState.Error -> {
                println(" EchoWebSocketListener error")
                binding.loader.visibility = View.GONE
            }

            is ScreenUiState.Ready -> {
                data.message?.let { message ->
                    adapter.addItem(Message(message = message, isFromSender = false))
                }
                binding.loader.visibility = View.GONE
            }
        }
    }

    private fun setUpEditTextView() {
        binding.etMessage.doAfterTextChanged {
            setupButtonSend(it.toString())
        }
    }

    private fun setupButtonSend(message: String) {
        binding.btnSend.isEnabled = message.isNotBlank()
        binding.btnSend.setOnClickListener {
            viewModel.sendMessage(message) {
                adapter.addItem(Message(message = message, isFromSender = true))
            }
        }
    }
}
