package com.techjd.devconnector.ui.fragments.Chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.chat.messages.Messages
import com.techjd.devconnector.data.models.chat.messages.MessagesItem

class MessageAdapter(val messages: Messages, val myUserId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    class View1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ourMessage: TextView = itemView.findViewById(R.id.ourMessage)
    }

    class View2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val otherMessage: TextView = itemView.findViewById(R.id.otherMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return View1ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_chat_ourself, parent, false)
            )
        }
        return View2ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_chat_other, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (message.from == myUserId) {
            (holder as View1ViewHolder).ourMessage.text = message.body
        } else {
            (holder as View2ViewHolder).otherMessage.text = message.body
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addItem(messagesItem: MessagesItem) {
        messages.add(messagesItem)
        Log.d("LAST ITEM", "Last Item ${messages[messages.size - 1]}")
        Log.d("SIZE", "addItem: ${messages.size}")
    }

    fun returnLastPosition(): Int {
        return messages.size - 1
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].from == myUserId) {
            return VIEW_TYPE_ONE
        }
        return VIEW_TYPE_TWO
    }
}