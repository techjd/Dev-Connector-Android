package com.techjd.devconnector.ui.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.data.models.jobs.alljobs.AllJobs
import com.techjd.devconnector.data.models.jobs.alljobs.UserObj

class JobsAdapter(
    val jobs: AllJobs,
    val onClickListener: (View, String, String, UserObj, Int) -> Unit,
    val myUserId: String
) : RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {

    class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.userName)
        val lookingFor: TextView = itemView.findViewById(R.id.lookingForContent)
        val shortDescription: TextView = itemView.findViewById(R.id.descContent)
        val requiredSkills: TextView = itemView.findViewById(R.id.requiredSkillsContent)
        val budgetContent: TextView = itemView.findViewById(R.id.budgetContent)
        val apply: Button = itemView.findViewById(R.id.applyJob)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_jobs,
            parent,
            false
        )
        return JobsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job = jobs[position]
        holder.username.text = job.userObj[0].name
        holder.lookingFor.text = job.lookingFor
        holder.shortDescription.text = job.shortDescription
        holder.requiredSkills.text = job.requiredSkills
        holder.budgetContent.text = job.budget
        if (myUserId == job.user) {
            holder.apply.text = "EDIT"
        }
        holder.apply.setOnClickListener {
            if (myUserId == job.user) {
                onClickListener.invoke(it, job.user, job._id, job.userObj[0], 1)
            } else {
                onClickListener.invoke(it, job.user, job._id, job.userObj[0], 2)
            }

        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
}