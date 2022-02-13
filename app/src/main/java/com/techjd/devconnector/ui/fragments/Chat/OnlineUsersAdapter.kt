package com.techjd.devconnector.ui.fragments.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.chat.onlineusers.OnlineUsers
import de.hdodenhof.circleimageview.CircleImageView

class OnlineUsersAdapter(
    val onlineUsers: OnlineUsers,
    val myUserId: String,
    val onClickListener: (View, String, String) -> Unit
) :
    RecyclerView.Adapter<OnlineUsersAdapter.OnlineUsersViewHolder>() {

    class OnlineUsersViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val cardView: CardView = itemview.findViewById(R.id.cardViewOnlineUser)
        val image: CircleImageView = itemview.findViewById(R.id.circularImageProfile)
        val userName: TextView = itemview.findViewById(R.id.onlineUserName)
        val userBio: TextView = itemview.findViewById(R.id.userBio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineUsersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_online_user,
            parent,
            false
        )
        return OnlineUsersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OnlineUsersViewHolder, position: Int) {
        val singleUser = onlineUsers[position]
        if (singleUser.userObj[0]._id == myUserId) {
            holder.userName.text = "${singleUser.userObj[0].name} YOU"
        } else {
            holder.userName.text = singleUser.userObj[0].name
        }
        holder.cardView.setOnClickListener {
            if (singleUser.userObj[0]._id != myUserId) {
                onClickListener.invoke(it, singleUser.userObj[0]._id, singleUser.userObj[0].name)
            }
        }
    }

    override fun getItemCount(): Int {
        return onlineUsers.size
    }
}