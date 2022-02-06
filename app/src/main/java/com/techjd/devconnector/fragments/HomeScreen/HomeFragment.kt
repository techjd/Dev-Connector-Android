package com.techjd.devconnector.fragments.HomeScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.PostsAdapter
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.viewmodels.PostsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var materialToolbar: MaterialToolbar
    lateinit var postsRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var mydataStore: DataStore
    val postsViewModel: PostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())

        postsRecyclerView = view.findViewById(R.id.postsRecyclerView)
        progressBar = view.findViewById(R.id.progressBar2)
        postsRecyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            postsViewModel.getPosts(mydataStore.getToken().first().toString())
        }

        postsViewModel.postLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    postsRecyclerView.adapter = PostsAdapter(response.data!!)
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d("Home Fragment", "onViewCreated: ${response.status}")

                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Log.d("Home Fragment", "onViewCreated: ${response.status}")

                }
            }
        }


    }

}