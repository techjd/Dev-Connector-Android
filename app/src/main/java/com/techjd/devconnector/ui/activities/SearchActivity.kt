package com.techjd.devconnector.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.profiles.ProfileItem
import com.techjd.devconnector.ui.fragments.ProfilesAdapter
import com.techjd.devconnector.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    lateinit var topSearchBar: MaterialToolbar
    lateinit var edtSearch: EditText
    lateinit var profilesRv: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var profileList: ArrayList<ProfileItem>
    lateinit var profilesAdapter: ProfilesAdapter
    val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        topSearchBar = findViewById(R.id.topSearchBar)
        edtSearch = findViewById(R.id.edtSearch)
        profilesRv = findViewById(R.id.searchResults)
        progressBar = findViewById(R.id.searchProgressBar)

        profilesRv.layoutManager = LinearLayoutManager(this)

        topSearchBar.setNavigationOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            profileViewModel.getAllProfile()
        }

        profileViewModel.allProfile.observe(this) { profiles ->
            when (profiles.status) {
                Status.SUCCESS -> {
                    profilesAdapter = ProfilesAdapter(
                        profiles.data!!,
                        onClickListener = { view, s ->
                            Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
                        }
                    )
                    profilesRv.adapter = profilesAdapter
                    profileList = profiles.data!!
                    edtSearch.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                }
            }
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

    }

    private fun filter(text: String) {
        val filteredList: ArrayList<ProfileItem> = ArrayList()
        for (item in profileList) {
            if (item.user.name.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(item)
            }
        }
        profilesAdapter.filterList(filteredList)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
    }
}