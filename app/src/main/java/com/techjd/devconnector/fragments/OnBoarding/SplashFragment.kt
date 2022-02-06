package com.techjd.devconnector.fragments.OnBoarding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.dataStore
import com.techjd.devconnector.activities.OnBoardingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
    lateinit var mydataStore: com.techjd.devconnector.Utils.DataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mydataStore = com.techjd.devconnector.Utils.DataStore(requireContext())

        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000L)
            Log.d("SPLASH ", "Is token presented ? : ${mydataStore.getToken().first()}")
            if (mydataStore.getToken().first() == null) {
                findNavController().navigate(R.id.action_splashFragment_to_selectLogInSignUpFragment2)
            } else{
                findNavController().navigate(R.id.action_splashFragment_to_mainActivity3)
                (activity as OnBoardingActivity).finish()
            }
        }
    }

}