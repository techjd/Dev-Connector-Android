package com.techjd.devconnector.ui.fragments.OnBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.textview.MaterialTextView
import com.techjd.devconnector.R


class SelectLogInSignUpFragment : Fragment() {
    private lateinit var signIn: MaterialTextView
    private lateinit var signUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_log_in_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signIn = view.findViewById(R.id.signIn)
        signUp = view.findViewById(R.id.signUp)

        signIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_selectLogInSignUpFragment_to_signInFragment)
        }
        signUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_selectLogInSignUpFragment_to_addNameFragment)
        }

    }

}