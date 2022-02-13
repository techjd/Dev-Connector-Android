package com.techjd.devconnector.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.profiles.ProfileBody
import com.techjd.devconnector.viewmodels.ProfileViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CreateProfileActivity : AppCompatActivity() {
    lateinit var topBar: MaterialToolbar
    lateinit var myDataStore: DataStore
    lateinit var edtTxtProfessionalStatus: TextInputEditText
    lateinit var edtTxtCompanyName: TextInputEditText
    lateinit var edtTxtSkillSet: TextInputEditText
    lateinit var edtTxtLocation: TextInputEditText
    lateinit var edtTxtBio: TextInputEditText
    lateinit var createProfile: Button
    lateinit var prorgress: ProgressBar
    val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        topBar = findViewById(R.id.createJobToolBar)
        myDataStore = DataStore(this)
        edtTxtProfessionalStatus = findViewById(R.id.professionalStatus)
        edtTxtCompanyName = findViewById(R.id.companyName)
        edtTxtSkillSet = findViewById(R.id.skillSet)
        edtTxtLocation = findViewById(R.id.location)
        edtTxtBio = findViewById(R.id.bio)
        createProfile = findViewById(R.id.editJob)
        prorgress = findViewById(R.id.addJobProgressBar)

        createProfile.setOnClickListener {
            lifecycleScope.launch {
                profileViewModel.createProfile(
                    myDataStore.getToken().first()!!,
                    ProfileBody(
                        status = edtTxtProfessionalStatus.text.toString().trim(),
                        company = edtTxtCompanyName.text.toString().trim(),
                        skills = edtTxtSkillSet.text.toString().trim(),
                        location = edtTxtLocation.text.toString().trim(),
                        bio = edtTxtBio.text.toString().trim(),
                    )
                )
            }
        }

        profileViewModel.profileResponse.observe(this) { profileResponse ->
            when (profileResponse.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "Profile Created SuccessFully", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    prorgress.visibility = View.GONE
                }
                Status.LOADING -> {
                    prorgress.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    prorgress.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
    }
}