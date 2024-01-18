package com.txtlabs.openeye.ui.retinopathy

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.net.toUri
import com.github.dhaval2404.imagepicker.ImagePicker
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.databinding.ActivityDetailRetinopathyBinding
import java.io.File

class DetailRetinopathyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRetinopathyBinding
    private lateinit var viewModel: RetinopathyViewModel
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRetinopathyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = RetinopathyViewModel()

        val uri = intent.getStringExtra(EXTRA_URI)?.toUri()
        Log.d("CEK_URI_DETAIL", "$uri")
        binding.imgView.setImageURI(uri)

        val file = uri?.path?.let { File(it) }
        viewModel.getFile = file

        take()
        predict()
    }

    private fun take(){
        binding.btnCamera.setOnClickListener {
            ImagePicker.with(this@DetailRetinopathyActivity)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
    }

    private fun predict() {
        binding.btnPredict.setOnClickListener {
            viewModel.postRetinopathy()
            viewModel.postRetinopathy.observe(this@DetailRetinopathyActivity) {
                when (it) {
                    is ResultState.Success -> {
                        Toast.makeText(this@DetailRetinopathyActivity, "Success", Toast.LENGTH_SHORT).show()
                        Log.d("RetinopathyActivity", "onCreate: ${it.value.kumilcintabh}")
                        binding.apply {
                            tvDeteksi.text = it.value.label ?: "No Title"

                            val descGeneral = it.value.kumilcintabh?.generalRecommendation?.joinToString(".") ?: "No Description"
                            val parts = descGeneral.split(".")
                            val sb = StringBuilder()
                            for (i in parts.indices) {
                                sb.append(parts[i])
                                sb.append("\n")
                            }
                            val desc = sb.toString()
                            Log.d("AKU", "$desc")
                            tvRekomendasi.text = desc

                            val descSpecific = it.value.kumilcintabh?.specificRecomendation?.joinToString(".") ?: "No Description"
                            val parts2 = descSpecific.split(".")
                            val sb2 = StringBuilder()
                            for (i in parts2.indices) {
                                sb2.append(parts2[i])
                                sb2.append("\n")
                            }
                            val desc2 = sb2.toString()
                            Log.d("AKU", "$desc2")
                            tvInformasi.text = desc2

                            binding.loading.visibility = GONE
                            cardView.visibility = VISIBLE
                            rekomendasi.visibility = VISIBLE
                            tvRekomendasi.visibility = VISIBLE
                            informasi.visibility = VISIBLE
                            tvInformasi.visibility = VISIBLE
                            btnPredict.visibility = GONE
                            btnCamera.visibility = GONE
                        }
                    }
                    is ResultState.Failure -> {
                        Toast.makeText(this@DetailRetinopathyActivity, "Failure", Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Loading -> {
                        binding.loading.visibility = VISIBLE
                        Toast.makeText(this@DetailRetinopathyActivity, "Loading", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this@DetailRetinopathyActivity, "Failure", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                uri.let { this.uri = it }

                Log.d("CEK_URI", "$uri")
                Intent(this, DetailRetinopathyActivity::class.java).also {
                    it.putExtra(EXTRA_URI, uri.toString())
                    startActivity(it)
                    finish()
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_URI = "extra_uri"
    }
}

