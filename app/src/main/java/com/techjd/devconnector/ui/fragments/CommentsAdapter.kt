package com.techjd.devconnector.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.UserPosts.SinglePost.Comment
import de.hdodenhof.circleimageview.CircleImageView

class CommentsAdapter(var comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.findViewById(R.id.cirularImageProfile)
        val name: TextView = itemView.findViewById(R.id.userName)
        val postComment: TextView = itemView.findViewById(R.id.userComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_comments,
            parent,
            false
        )
        return CommentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments[position]
        holder.name.text = comment.name
        holder.postComment.text = comment.text
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}