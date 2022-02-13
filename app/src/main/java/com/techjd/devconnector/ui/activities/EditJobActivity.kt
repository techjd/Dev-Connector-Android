package com.techjd.devconnector.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.techjd.devconnector.R
import com.techjd.devconnector.Utils.DataStore
import com.techjd.devconnector.Utils.Status
import com.techjd.devconnector.data.models.jobs.JobBody
import com.techjd.devconnector.viewmodels.JobsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditJobActivity : AppCompatActivity() {
    lateinit var topJobBar: MaterialToolbar
    lateinit var edtTxtLookingFor: TextInputEditText
    lateinit var edtTxtShortDesc: TextInputEditText
    lateinit var edtRequiredskills: TextInputEditText
    lateinit var edtBudget: TextInputEditText
    lateinit var myDataStore: DataStore
    lateinit var addJob: ProgressBar
    lateinit var editBtn: Button
    val jobsViewModel: JobsViewModel by viewModels()
    lateinit var jobId: String
    val args: EditJobActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job)

        topJobBar = findViewById(R.id.topJobToolBar)
        topJobBar.setNavigationOnClickListener {
            onBackPressed()
        }
        topJobBar.title = "Edit Job"
        myDataStore = DataStore(this)

        Log.d("TAG", "onCreate: ${args.id}")
        edtTxtLookingFor = findViewById(R.id.lookingFor)
        edtTxtShortDesc = findViewById(R.id.shortDescription)
        edtRequiredskills = findViewById(R.id.requiredSkills)
        edtBudget = findViewById(R.id.yourBudget)
        editBtn = findViewById(R.id.editJob)
        addJob = findViewById(R.id.addJobProgressBar)

        editBtn.setOnClickListener {
            lifecycleScope.launch {
                jobsViewModel.updateASinglePost(
                    myDataStore.getToken().first()!!,
                    args.id,
                    JobBody(
                        edtTxtLookingFor.text.toString(),
                        edtTxtShortDesc.text.toString(),
                        edtRequiredskills.text.toString(),
                        edtBudget.text.toString()
                    )
                )
            }
        }

        lifecycleScope.launch {
            jobsViewModel.getASingleJob(
                myDataStore.getToken().first()!!,
                args.id
            )
        }

        jobsViewModel.updateJob.observe(this) { msg ->
            when (msg.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, msg.data!!.msg, Toast.LENGTH_SHORT).show()
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


        jobsViewModel.singleJob.observe(this) { singleJob ->
            when (singleJob.status) {
                Status.SUCCESS -> {
                    edtTxtLookingFor.setText(singleJob.data?.lookingFor)
                    edtTxtShortDesc.setText(singleJob.data?.shortDescription)
                    edtRequiredskills.setText(singleJob.data?.requiredSkills)
                    edtBudget.setText(singleJob.data?.budget)
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