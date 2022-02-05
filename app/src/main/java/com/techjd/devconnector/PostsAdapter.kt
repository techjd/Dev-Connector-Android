package com.techjd.devconnector

import android.text.Html
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.data.models.UserPosts.Posts
import com.techjd.devconnector.data.models.UserPosts.PostsItem
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class PostsAdapter(val posts: Posts) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.findViewById(R.id.cirularImageProfile)
        val name: TextView = itemView.findViewById(R.id.userName)
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
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}