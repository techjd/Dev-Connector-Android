package com.techjd.devconnector.ui.fragments.OnBoarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.R
import com.techjd.devconnector.viewmodels.SignUpViewModel

class AddEmailFragment : Fragment() {

    lateinit var btnPassword: MaterialButton
    lateinit var emailEdtText: TextInputEditText
    lateinit var materialToolbar: MaterialToolbar
    val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnPassword = view.findViewById(R.id.continueToPassword)
        emailEdtText = view.findViewById(R.id.emailAddress)
        materialToolbar = view.findViewById(R.id.topAppBar)
        materialToolbar.menu.clear()
        Log.d("FIRST NAME", "onViewCreated: ${signUpViewModel.getName()} ${signUpViewModel.secondName.value}")

        signUpViewModel.email.observe(viewLifecycleOwner) { email ->
            emailEdtText.setText(email)
        }

        btnPassword.setOnClickListener {
            signUpViewModel.saveEmail(emailEdtText.text.toString().trim())
            findNavController().navigate(R.id.action_addEmailFragment_to_setPasswordFragment)
        }
    }

}