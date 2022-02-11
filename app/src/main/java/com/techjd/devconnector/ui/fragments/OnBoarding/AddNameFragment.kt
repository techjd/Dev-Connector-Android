package com.techjd.devconnector.ui.fragments.OnBoarding

import android.os.Bundle
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

class AddNameFragment : Fragment() {

    lateinit var btnContinue: MaterialButton
    lateinit var firstName: TextInputEditText
    lateinit var secondName: TextInputEditText
    lateinit var materialToolbar: MaterialToolbar
    val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnContinue = view.findViewById(R.id.continueToAddEmail)

        firstName = view.findViewById(R.id.firstName)
        secondName = view.findViewById(R.id.secondName)
        materialToolbar = view.findViewById(R.id.topAppBar)
        materialToolbar.menu.clear()

        signUpViewModel.firstName.observe(viewLifecycleOwner) { fN ->
            firstName.setText(fN)
        }

        signUpViewModel.secondName.observe(viewLifecycleOwner) { sN ->
            secondName.setText(sN)
        }

        btnContinue.setOnClickListener {
            signUpViewModel.saveFirstNameAndSecond(
                firstName.text.toString(),
                secondName.text.toString()
            )
            findNavController().navigate(R.id.action_addNameFragment_to_addEmailFragment)
        }

    }

}