package com.techjd.devconnector.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techjd.devconnector.R
import com.techjd.devconnector.ui.activities.AddJobActivity
import com.techjd.devconnector.ui.activities.AddPostActivity
import com.techjd.devconnector.ui.activities.MessagingActivity

class ModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var newJobLayout: LinearLayout
    lateinit var newPostLayout: LinearLayout

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newJobLayout = view.findViewById(R.id.newJob)
        newPostLayout = view.findViewById(R.id.newLifeUpdate)

        newPostLayout.setOnClickListener {
            navigateToAddNewPost()
        }

        newJobLayout.setOnClickListener {
            navigateToAddNewJob()
        }

    }

    fun navigateToAddNewPost() {
        val navigate = Intent(context, AddPostActivity::class.java)
        startActivity(navigate)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation)
    }

    fun navigateToAddNewJob() {
        val navigate = Intent(context, AddJobActivity::class.java)
        startActivity(navigate)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation)
    }


}