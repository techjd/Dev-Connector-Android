package com.techjd.devconnector.ui.fragments.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.chat.onlineusers.OnlineUsers
import de.hdodenhof.circleimageview.CircleImageView

class OnlineUsersAdapter(val onlineUsers: OnlineUsers) :
    RecyclerView.Adapter<OnlineUsersAdapter.OnlineUsersViewHolder>() {

    class OnlineUsersViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
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
        holder.userName.text = singleUser.userObj[0].name
//        holder.userBio.text = singleUser.userObj[0].bio
    }

    override fun getItemCount(): Int {
        return onlineUsers.size
    }
}