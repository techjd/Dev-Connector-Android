package com.techjd.devconnector.ui.fragments.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import de.hdodenhof.circleimageview.CircleImageView

class ConversationsAdapter(
    val conversations: Conversations,
    val myUserId: String,
    val onClickListener: (View, String, String) -> Unit
) :
    RecyclerView.Adapter<ConversationsAdapter.ConversationsViewHolder>() {

    class ConversationsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image: CircleImageView = itemview.findViewById(R.id.circularImageProfile)
        val userName: TextView = itemview.findViewById(R.id.convoUserName)
        val lastMessage: TextView = itemview.findViewById(R.id.userLastMessage)
        val cardView: CardView = itemview.findViewById(R.id.cardView)
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
        val toId = conversations[position].recipientObj
        var userName: String? = null
        var idToSend: String? = null
        for (i in 0..toId.size) {
            if (toId[i]._id != myUserId) {
                idToSend = toId[i]._id
                userName = toId[i].name
                break
            }
        }
        holder.userName.text = userName
        holder.lastMessage.text = singleUser.lastMessage
        holder.cardView.setOnClickListener { view ->
            onClickListener.invoke(view, idToSend!!, userName!!)
        }
    }

    override fun getItemCount(): Int {
        return conversations.size
    }
}