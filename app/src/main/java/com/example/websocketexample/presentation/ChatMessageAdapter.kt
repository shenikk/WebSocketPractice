package com.example.websocketexample.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.websocketexample.databinding.ItemMessageBinding

class ChatMessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    private val items = arrayListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItem(item: Message) {
        this.items.add(item)
        notifyDataSetChanged() // TODO поправить
    }
}
