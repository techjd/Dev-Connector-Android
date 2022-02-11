package com.techjd.devconnector.ui.fragments.OnBoarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.viewmodels.SignUpViewModel
import com.techjd.devconnector.ui.activities.OnBoardingActivity
import kotlinx.coroutines.launch

class SetPasswordFragment : Fragment() {

    lateinit var btnAgreeAndJoin: MaterialButton
    lateinit var passWordEdt: TextInputEditText
    lateinit var emailEdt: TextInputEditText
    lateinit var materialToolbar: MaterialToolbar
    lateinit var progressBar: ProgressBar
    lateinit var mydataStore: DataStore
    val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_set_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAgreeAndJoin = view.findViewById(R.id.agreeAndJoin)
        passWordEdt = view.findViewById(R.id.password)
        emailEdt = view.findViewById(R.id.emailAddress)
        progressBar = view.findViewById(R.id.registerProgressBar)
        materialToolbar = view.findViewById(R.id.topAppBar)
        materialToolbar.menu.clear()
        mydataStore = DataStore(requireContext())

        Log.d("EMAIL ", "onViewCreated: ${signUpViewModel.firstName.value}")

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            signUpViewModel.saveEmail(emailEdt.text.toString())
            signUpViewModel.savePassword(passWordEdt.text.toString())
            findNavController().navigate(R.id.action_setPasswordFragment_to_addEmailFragment)
        }

        signUpViewModel.resource.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        context,
                        "User Registration SuccessFul",
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                    lifecycleScope.launch {
                        mydataStore.saveToken(response.data!!.token)
                        mydataStore.saveUserId(response.data.user._id)

                    }
                    findNavController().navigate(R.id.action_setPasswordFragment_to_mainActivity)
                    (activity as OnBoardingActivity).finish()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        }

        signUpViewModel.email.observe(viewLifecycleOwner) { email ->
            emailEdt.setText(email)
        }

        signUpViewModel.password.observe(viewLifecycleOwner) { password ->
            passWordEdt.setText(password)
        }

        btnAgreeAndJoin.setOnClickListener {
            signUpViewModel.saveEmail(emailEdt.text.toString())
            signUpViewModel.savePassword(passWordEdt.text.toString())
            lifecycleScope.launch {
                signUpViewModel.registerUser()
            }
        }
    }

}