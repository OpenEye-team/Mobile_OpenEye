package com.txtlabs.openeye.ui.tracker.add

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.databinding.ActivityAddTrackerBinding
import com.txtlabs.openeye.ui.NavigationActivity
import com.txtlabs.openeye.ui.tracker.TrackerViewModel
import com.txtlabs.openeye.utils.ConfirmDialog

class AddTrackerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTrackerBinding
    private lateinit var viewModel: TrackerViewModel
    private lateinit var datePickerDialog: DatePickerDialog

    private var date: String = ""
    private var time: String = ""
    private var glucose: Int = 0
    private var meals: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        viewModel = TrackerViewModel()

        binding.ivBack.setOnClickListener {
            finish()
        }

        setSpinner()
        setDate()
        setTime()

        binding.apply {
            btnSubmit.setOnClickListener {
                glucose = 0
                glucose = if (etGlucose.text?.isEmpty() == true) {
                    0
                } else {
                    etGlucose.text.toString().toInt()
                }

                viewModel.checkInputHistory(date, time, glucose, meals)
                viewModel.cekInputHistory.observe(this@AddTrackerActivity) {
                    when (it) {
                        is ResultState.Success -> {
                            Toast.makeText(this@AddTrackerActivity, "Success", Toast.LENGTH_LONG).show()
                            val dialog = ConfirmDialog()
                            dialog.title = "Success, Your glucose history has been added"
                            dialog.description = "Do you want to add another glucose history?"
                            dialog.positiveText = "OK"
                            dialog.positiveCallback = {
                                dialog.dismiss()
                                val intent = Intent(this@AddTrackerActivity, AddTrackerActivity::class.java)
                                startActivity(intent)
                            }
                            dialog.negativeText = "Cancel"
                            dialog.negativeCallback = {
                                dialog.dismiss()
                                finish()
                                val intent = Intent(this@AddTrackerActivity, NavigationActivity::class.java)
                                startActivity(intent)
                            }
                            dialog.show(this@AddTrackerActivity.supportFragmentManager, "confirm_dialog")
                        }

                        is ResultState.Failure -> {
                            val dialog = ConfirmDialog()
                            dialog.title = "Error"
                            dialog.description = "Your glucose history has not been added"
                            dialog.positiveText = "OK"
                            dialog.positiveCallback = {
                                dialog.dismiss()
                            }
                            dialog.show(this@AddTrackerActivity.supportFragmentManager, "confirm_dialog")
                            Toast.makeText(this@AddTrackerActivity, it.throwable.toString(), Toast.LENGTH_LONG).show()
                        }

                        else -> {
                            Toast.makeText(this@AddTrackerActivity, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setSpinner() {
        binding.apply {
            val items = listOf("Before meal", "after meal", "fasting")
            val adapter =
                ArrayAdapter(this@AddTrackerActivity, android.R.layout.simple_spinner_dropdown_item, items)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    meals = items[position]
                    Log.d("spinner", meals)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    meals = items[0]
                    Log.d("spinner", meals)
                }
            }
        }
    }

    private fun setDate() {
        binding.apply {
            cvTanggal.setOnClickListener {
                datePickerDialog = DatePickerDialog(this@AddTrackerActivity)
                datePickerDialog.show()

                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    tvHintTanggal.text = "$dayOfMonth/$month/$year"
                    Log.d("date", "$year $month $dayOfMonth")
                    date = "$year/$month/$dayOfMonth"
                }
            }

            tvHintTanggal.setOnClickListener {
                datePickerDialog = DatePickerDialog(this@AddTrackerActivity)
                datePickerDialog.show()

                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    tvHintTanggal.text = "$dayOfMonth/$month/$year"
                    Log.d("date", "$year $month $dayOfMonth")
                    date = "$year/$month/$dayOfMonth"
                }
            }
        }
    }

    private fun setTime() {
        binding.apply {
            cvTime.setOnClickListener {
                tmTimePicker.visibility = View.VISIBLE
                tvHintTime.visibility = View.GONE
                tmTimePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                    Log.d("time", "$hourOfDay $minute")
                    time = "$hourOfDay:$minute"
                }
            }

            tvHintTime.setOnClickListener {
                tmTimePicker.visibility = View.VISIBLE
                tvHintTime.visibility = View.GONE
                tmTimePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                    Log.d("time", "$hourOfDay $minute")
                    time = "$hourOfDay:$minute"
                }
            }
        }
    }
}