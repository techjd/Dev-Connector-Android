package com.techjd.devconnector.ui.fragments.HomeScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.chat.sendMessages.SendMessageBody
import com.techjd.devconnector.ui.activities.AddJobActivity
import com.techjd.devconnector.ui.activities.EditJobActivity
import com.techjd.devconnector.ui.fragments.JobsAdapter
import com.techjd.devconnector.viewmodels.ChatViewModel
import com.techjd.devconnector.viewmodels.JobsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class JobsFragment : Fragment() {
    lateinit var jobsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var mydataStore: DataStore
    val jobsViewModel: JobsViewModel by viewModels()
    val chatViewModel: ChatViewModel by viewModels()
    lateinit var myuserid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobsRecyclerView = view.findViewById(R.id.jobsRecyclerView)
        jobsRecyclerView.layoutManager = LinearLayoutManager(context)
        progressBar = view.findViewById(R.id.loadJobs)
        mydataStore = DataStore(requireContext())

        runBlocking {
            myuserid = mydataStore.getUserId().first()!!
        }

        lifecycleScope.launch {
            jobsViewModel.getAllJobs(
                mydataStore.getToken().first()!!
            )
        }

        chatViewModel.sendMessage.observe(viewLifecycleOwner) { message ->
            when (message.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Message SuccessFully Sent", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        }

        jobsViewModel.allJobs.observe(viewLifecycleOwner) { jobs ->
            when (jobs.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    jobsRecyclerView.adapter =
                        JobsAdapter(
                            jobs.data!!,
                            onClickListener = { view, userId, jobId, userObj, type ->
                                if (type == 1) {
                                    val action =
                                        JobsFragmentDirections.actionJobsFragmentToEditJobActivity(
                                            jobId
                                        )
                                    findNavController().navigate(action)
                                    Toast.makeText(context, "Own Post", Toast.LENGTH_SHORT).show()
                                } else if (type == 2) {
                                    lifecycleScope.launch {
                                        chatViewModel.sendMessage(
                                            mydataStore.getToken().first()!!,
                                            SendMessageBody(
                                                userId,
                                                "Hey ${userObj.name} , I am Interested in Your Opportunity . Give Me Further Details ."
                                            )
                                        )
                                    }
                                }
                            },
                            myuserid
                        )

                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        }


    }

}