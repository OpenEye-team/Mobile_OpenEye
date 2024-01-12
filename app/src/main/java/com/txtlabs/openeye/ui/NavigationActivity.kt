package com.txtlabs.openeye.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController
import com.github.dhaval2404.imagepicker.ImagePicker


import com.txtlabs.openeye.R

import com.txtlabs.openeye.databinding.ActivityNavigationBinding
import com.txtlabs.openeye.ui.retinopathy.DetailRetinopathyActivity

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Open Eye"

        val navView: BottomNavigationView = binding.bottomnavigationbar
        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)
        navView.setupWithNavController(navController)

        binding.apply {
            fab.setOnClickListener {
                ImagePicker.with(this@NavigationActivity)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
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
                    it.putExtra(DetailRetinopathyActivity.EXTRA_URI, uri.toString())
                    startActivity(it)
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
}