package com.example.websocketexample

import androidx.recyclerview.widget.RecyclerView
import com.example.websocketexample.databinding.ItemMessageBinding

class MessageViewHolder(private val binding: ItemMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Message) {
        if (item.isFromSender) showMessageFromReceiver(item.message)
        else showMessageFromSender(item.message)
    }

    private fun showMessageFromSender(message: String) {
        binding.root.displayedChild = 0
        binding.tvMessageSender.text = message
    }

    private fun showMessageFromReceiver(message: String) {
        binding.root.displayedChild = 1
        binding.tvMessageReceiver.text = message
    }
}
