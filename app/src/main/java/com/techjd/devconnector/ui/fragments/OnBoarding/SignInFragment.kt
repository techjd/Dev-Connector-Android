package com.techjd.devconnector.ui.fragments.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.ui.activities.MainActivity
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.ui.activities.OnBoardingActivity
import com.techjd.devconnector.viewmodels.SignInViewModel
import kotlinx.coroutines.launch


class SignInFragment : Fragment() {
    private val TAG = "SignInFragment"
    lateinit var materialToolbar: MaterialToolbar
    lateinit var emailTextInputLayout: TextInputEditText
    lateinit var passwordTextInputLayout: TextInputEditText
    lateinit var signIn: MaterialButton
    lateinit var progressBar: ProgressBar
    lateinit var mydataStore: DataStore
    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        materialToolbar = view.findViewById(R.id.topAppBar)
        emailTextInputLayout = view.findViewById(R.id.userName)
        passwordTextInputLayout = view.findViewById(R.id.passWord)
        signIn = view.findViewById(R.id.logIn)
        progressBar = view.findViewById(R.id.progressBar)
        mydataStore = DataStore(requireContext())


        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signun_or_login -> {
                    findNavController().navigate(R.id.action_signInFragment_to_addNameFragment)
                    true
                }
                else -> false
            }
        }

        signInViewModel._responseLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        context,
                        "Login SuccessFul",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "Success: ${response.data}")
                    lifecycleScope.launch {
                        mydataStore.saveToken(response.data!!.token)
                        mydataStore.saveUserId(response.data.user._id)
                    }
                    progressBar.visibility = View.GONE
                    Intent(context,  MainActivity::class.java).apply {
                        startActivity(this)
                    }
                    (activity as OnBoardingActivity).finish()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "Loading: ${response.message}")
                }
                Status.ERROR -> {
                    Log.d(TAG, "Error: ${response.message}")
                    progressBar.visibility = View.GONE
                }
            }
        }

        signIn.setOnClickListener {
            if (emailTextInputLayout.text.toString().trim() != "" && passwordTextInputLayout.text.toString().trim() != "") {
                signInViewModel.loginUser(
                    emailTextInputLayout.text.toString().trim(),
                    passwordTextInputLayout.text.toString().trim()
                )
            } else {
                Toast.makeText(context, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}