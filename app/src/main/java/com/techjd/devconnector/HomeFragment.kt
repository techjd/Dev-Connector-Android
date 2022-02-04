package com.techjd.devconnector

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar

class HomeFragment : Fragment() {

    lateinit var materialToolbar: MaterialToolbar

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
//        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
//        val message = view.findViewById<ImageView>(R.id.navigateToMessage)
//        message.setOnClickListener {
//            (activity as MainActivity).navigateToMessagingActivity()
//        }
//        val profile = view.findViewById<ImageView>(R.id.profile)
//        profile.setOnClickListener {
//            (activity as MainActivity).navigateToProfileActivity()
//        }
//        val search = view.findViewById<ImageView>(R.id.navigateToSearch)
//        search.setOnClickListener {
//            (activity as MainActivity).navigateToSearchActuvity()
//        }



    }

}