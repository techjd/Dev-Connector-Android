package com.techjd.devconnector.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.PostsAdapter
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import de.hdodenhof.circleimageview.CircleImageView

class ConversationsAdapter(val conversations: Conversations) :
    RecyclerView.Adapter<ConversationsAdapter.ConversationsViewHolder>() {

    class ConversationsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image: CircleImageView = itemview.findViewById(R.id.circularImageProfile)
        val userName: TextView = itemview.findViewById(R.id.convoUserName)
        val lastMessage: TextView = itemview.findViewById(R.id.userLastMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_conversations,
            parent,
            false
        )
        return ConversationsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConversationsViewHolder, position: Int) {
        val singleUser = conversations[position]
        holder.userName.text = singleUser.recipientObj[1].name
        holder.lastMessage.text = singleUser.lastMessage
    }

    override fun getItemCount(): Int {
        return conversations.size
    }
}