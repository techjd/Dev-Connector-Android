package com.techjd.devconnector.ui.fragments

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.CommentBody
import com.techjd.devconnector.ui.activities.MainActivity
import com.techjd.devconnector.viewmodels.PostsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class FragmentDetailPost : Fragment() {
    lateinit var detailPostTopAppBar: MaterialToolbar
    lateinit var mydataStore: DataStore
    lateinit var singlePostProgressBar: ProgressBar
    lateinit var userName: TextView
    lateinit var totLikesCnt: TextView
    lateinit var totComments: TextView
    lateinit var postContent: TextView
    lateinit var postComment: Button
    lateinit var edtText: EditText
    lateinit var commentsRecyclerView: RecyclerView
    lateinit var commentAdapter: CommentsAdapter
    val postsViewModel: PostsViewModel by viewModels()
    val args: FragmentDetailPostArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = DataStore(requireContext())
        detailPostTopAppBar = view.findViewById(R.id.topAppBarDetailPost)
        singlePostProgressBar = view.findViewById(R.id.singlePostProgressBar)
        userName = view.findViewById(R.id.userName)
        postContent = view.findViewById(R.id.content)
        commentsRecyclerView = view.findViewById(R.id.commentsRecycerView)
        totLikesCnt = view.findViewById(R.id.totalLikesCnt)
        totComments = view.findViewById(R.id.totalComments)
        postComment = view.findViewById(R.id.postComment)
        edtText = view.findViewById(R.id.edtTxtComment)

        commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        detailPostTopAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
            (activity as MainActivity).apply {
                topAppBar.visibility = View.VISIBLE
                bottomNav.visibility = View.VISIBLE
            }
        }

        postComment.setOnClickListener {
            lifecycleScope.launch {
                postsViewModel.postAComment(
                    mydataStore.getToken().first()!!,
                    args.postId,
                    CommentBody(edtText.text.toString())
                )
            }
        }

        lifecycleScope.launch {
            postsViewModel.getSinglePost(
                mydataStore.getToken().first()!!,
                args.postId
            )
        }

        postsViewModel.comment.observe(viewLifecycleOwner) { posts ->
            when (posts.status) {
                Status.SUCCESS -> {
                    lifecycleScope.launch {
                        postsViewModel.getSinglePost(
                            mydataStore.getToken().first()!!,
                            args.postId
                        )
                    }
                    edtText.text?.clear()

//                    commentAdapter.comments = (posts.data!! as List<Comment>)
//                    lifecycleScope.launch {
//                        withContext(Dispatchers.Main) {
//                            commentAdapter.notifyDataSetChanged()
//                        }
//                    }
//                    edtText.text.clear()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        }


        postsViewModel.singlePostLiveData.observe(viewLifecycleOwner) { post ->
            when (post.status) {
                Status.SUCCESS -> {
                    userName.text = post.data?.name
                    postContent.text = Html.fromHtml(post.data?.text)
                    totLikesCnt.text = post.data?.likes?.size.toString()
                    totComments.text = "${post.data?.comments?.size} comments"
                    commentAdapter = CommentsAdapter(
                        post.data!!.comments
                    )
                    commentsRecyclerView.adapter = commentAdapter
                    singlePostProgressBar.visibility = View.GONE

                }
                Status.LOADING -> {
                    singlePostProgressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    singlePostProgressBar.visibility = View.GONE
                }
            }
        }
    }
}