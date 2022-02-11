package com.techjd.devconnector.ui.fragments

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.UserPosts.Posts
import de.hdodenhof.circleimageview.CircleImageView

class PostsAdapter(
    val posts: Posts,
    val onClickListener: (View, String) -> Unit
    ) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.findViewById(R.id.cirularImageProfile)
        val name: TextView = itemView.findViewById(R.id.userName)
        val linearLayoutPost: LinearLayout = itemView.findViewById(R.id.linearLayoutPost)
        val postContent: TextView = itemView.findViewById(R.id.content)
        val numberOfLikes: TextView = itemView.findViewById(R.id.totalLikesCnt)
        val numberOfComments: TextView = itemView.findViewById(R.id.totalComments)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_post,
            parent,
            false
        )
        return PostsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val singlePost = posts[position]
        holder.name.text = singlePost.name
        holder.postContent.text = Html.fromHtml(singlePost.text)
        holder.numberOfLikes.text = singlePost.likes.size.toString()
        holder.numberOfComments.text = "${singlePost.comments.size.toString()} Comments"
        holder.linearLayoutPost.setOnClickListener {
            onClickListener.invoke(holder.itemView, singlePost._id)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}