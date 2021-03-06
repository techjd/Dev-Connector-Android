package com.techjd.devconnector.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.jobs.JobBody
import com.techjd.devconnector.viewmodels.JobsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddJobActivity : AppCompatActivity() {
    lateinit var topJobBar: MaterialToolbar
    lateinit var edtTxtLookingFor: TextInputEditText
    lateinit var edtTxtShortDesc: TextInputEditText
    lateinit var edtRequiredskills: TextInputEditText
    lateinit var edtBudget: TextInputEditText
    lateinit var myDataStore: DataStore
    lateinit var addJob: ProgressBar
    lateinit var postBtn: Button
    val jobsViewModel: JobsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        topJobBar = findViewById(R.id.topJobToolBar)
        topJobBar.setNavigationOnClickListener {
            onBackPressed()
        }
        myDataStore = DataStore(this)

        edtTxtLookingFor = findViewById(R.id.lookingFor)
        edtTxtShortDesc = findViewById(R.id.shortDescription)
        edtRequiredskills = findViewById(R.id.requiredSkills)
        edtBudget = findViewById(R.id.yourBudget)
        postBtn = findViewById(R.id.postANewJob)
        addJob = findViewById(R.id.addJobProgressBar)

        postBtn.setOnClickListener {
            lifecycleScope.launch {
                jobsViewModel.postJob(
                    myDataStore.getToken().first()!!,
                    JobBody(
                        edtTxtLookingFor.text.toString(),
                        edtTxtShortDesc.text.toString(),
                        edtRequiredskills.text.toString(),
                        edtBudget.text.toString()
                    )
                )
            }
        }

        jobsViewModel.postJob.observe(this) { jobResponse ->
            when (jobResponse.status) {
                Status.SUCCESS -> {
                    edtTxtLookingFor.text?.clear()
                    edtTxtShortDesc.text?.clear()
                    edtRequiredskills.text?.clear()
                    edtBudget.text?.clear()
                    Toast.makeText(this, "Job Posted SuccessFully", Toast.LENGTH_SHORT).show()
                    addJob.visibility = View.GONE
                }
                Status.LOADING -> {
                    addJob.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    addJob.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
    }
}