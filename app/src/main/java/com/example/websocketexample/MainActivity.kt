package com.example.websocketexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.websocketexample.data.EchoWebSocketListener
import com.example.websocketexample.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChatMessageAdapter
    private lateinit var client: OkHttpClient
    private lateinit var webSocket: WebSocket
    private lateinit var viewModel: ChatViewModel

    companion object {
        private const val ECHO_URL = "wss://websocket-echo.glitch.me"
//        private const val ECHO_URL = "wss://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        viewModel.socketStatus.observe(this) {
            if (it) binding.toolbar.title = "Connecting..." else binding.toolbar.title = "Connected"
        }
        viewModel.message.observe(this) { message ->
            adapter.addItem(Message(message = message, isFromSender = false))
        }

        setupRecyclerViewMessage()
        setupWebSocketService()
        setUpEditTextView()
    }

    private fun setupRecyclerViewMessage() {
        adapter = ChatMessageAdapter()
        binding.recyclerMessage.apply {
            adapter = this@MainActivity.adapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun setupWebSocketService() {
        client = OkHttpClient()
        val request = Request.Builder()
            .url(ECHO_URL)
            .build()

        val listener = EchoWebSocketListener(viewModel)
        webSocket = client.newWebSocket(request, listener)
    }

    private fun setUpEditTextView() {
        binding.etMessage.doAfterTextChanged {
            setupButtonSend(it.toString())
        }
    }

    private fun setupButtonSend(message: String) {
        binding.btnSend.isEnabled = message.isNotBlank()
        binding.btnSend.setOnClickListener {
            sendMessage(message)
        }
    }

    private fun sendMessage(message: String) {
        if (::webSocket.isInitialized) {
            webSocket.send(message)
            adapter.addItem(Message(message = message, isFromSender = true))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.dispatcher.executorService.shutdown()
    }



    ////

//    private fun setupWebSocketService() {
//        webSocketService = provideWebSocketService(
//            scarlet = provideScarlet(
//                client = provideOkhttp(),
//                lifecycle = provideLifeCycle(),
//                streamAdapterFactory = provideStreamAdapterFactory(),
//            )
//        )
//    }
//
//    @SuppressLint("CheckResult")
//    private fun observeConnection() {
//        webSocketService.observeConnection()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ response ->
//                Log.d("observeConnection", response.toString())
//                onReceiveResponseConnection(response)
//            }, { error ->
//                Log.e("observeConnection", error.message.orEmpty())
//                Snackbar.make(binding.root, error.message.orEmpty(), Snackbar.LENGTH_SHORT).show()
//            })
//    }
//
//    private fun onReceiveResponseConnection(response: WebSocket.Event) {
//        when (response) {
//            is OnConnectionOpened<*> -> changeToolbarTitle("connection opened")
//            is OnConnectionClosed -> changeToolbarTitle("connection closed")
//            is OnConnectionClosing -> changeToolbarTitle("closing connection..")
//            is OnConnectionFailed -> changeToolbarTitle("connection failed")
//            is OnMessageReceived -> handleOnMessageReceived(response.message)
//        }
//    }
//
//    private fun handleOnMessageReceived(message: MessageScarlet) {
//        adapter.addItem(Message(message.toValue(), false))
//        binding.etMessage.setText("")
//    }
//
//    private fun MessageScarlet.toValue(): String {
//        return when (this) {
//            is Text -> value
//            is Bytes -> value.toString()
//        }
//    }
//
//    private fun changeToolbarTitle(title: String) {
//        binding.toolbar.title = title
//    }
//
//    private fun setupEditTextMessage() {
//        binding.etMessage.doAfterTextChanged {
//            setupButtonSend(it.toString())
//        }
//    }
//
//    private fun setupButtonSend(message: String) {
//        binding.btnSend.isEnabled = message.isNotBlank()
//        binding.btnSend.setOnClickListener { sendMessage(message) }
//    }
//
//    private fun sendMessage(message: String) {
//        webSocketService.sendMessage(message)
//        adapter.addItem(Message(message = message, isFromSender = true))
//    }
//
//    // TODO: 14/10/21 implement dependency injection and move it from activity
//    private fun provideWebSocketService(scarlet: Scarlet) = scarlet.create(EchoService::class.java)
//
//    // TODO: 14/10/21 implement dependency injection and move it from activity
//    private fun provideScarlet(
//        client: OkHttpClient,
//        lifecycle: Lifecycle,
//        streamAdapterFactory: StreamAdapter.Factory,
//    ) =
//        Scarlet.Builder()
//            .webSocketFactory(client.newWebSocketFactory(ECHO_URL))
//            .lifecycle(lifecycle)
//            .addStreamAdapterFactory(streamAdapterFactory)
//            .build()
//
//    // TODO: 14/10/21 implement dependency injection and move it from activity
//    private fun provideOkhttp() =
//        OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
//            .build()
//
//    // TODO: 14/10/21 implement dependency injection and move it from activity
//    private fun provideLifeCycle() = AndroidLifecycle.ofApplicationForeground(application)
//
//    // TODO: 14/10/21 implement dependency injection and move it from activity
//    private fun provideStreamAdapterFactory() = RxJava2StreamAdapterFactory()

}
