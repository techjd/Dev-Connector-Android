package com.techjd.devconnector.ui.fragments.HomeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techjd.devconnector.R


class NotificationsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val message = view.findViewById<ImageView>(R.id.navigateToMessage)
//        message.setOnClickListener {
//            (activity as MainActivity).navigateToMessagingActivity()
//
//        }

//        val profile = view.findViewById<ImageView>(R.id.profile)
//        profile.setOnClickListener {
//            (activity as MainActivity).navigateToProfileActivity()
//        }
    }

}