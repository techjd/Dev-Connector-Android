package com.techjd.devconnector.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.profiles.ProfileItem

class ProfilesAdapter(
    var profilesList: ArrayList<ProfileItem>,
    val onClickListener: (View, String) -> Unit,
) :
    RecyclerView.Adapter<ProfilesAdapter.ProfilesViewHolder>() {

    class ProfilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchCardView: CardView = itemView.findViewById(R.id.searchCardView)
        val userName: TextView = itemView.findViewById(R.id.searchUserName)
        val bio: TextView = itemView.findViewById(R.id.bio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        return ProfilesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_search_results,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        val profile = profilesList[position]
        holder.userName.text = profile.user.name
        holder.bio.text = profile.bio
        holder.searchCardView.setOnClickListener {
            onClickListener.invoke(it, profile.user._id)
        }
    }

    override fun getItemCount(): Int {
        return profilesList.size
    }

    fun filterList(filteredList: ArrayList<ProfileItem>) {
        profilesList = filteredList
        notifyDataSetChanged()
    }
}