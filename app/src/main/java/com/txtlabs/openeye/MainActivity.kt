package com.txtlabs.openeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.orhanobut.hawk.Hawk
import com.txtlabs.openeye.data.response.LoginResponse
import com.txtlabs.openeye.databinding.ActivityMainBinding
import com.txtlabs.openeye.ui.NavigationActivity
import com.txtlabs.openeye.ui.auth.login.LoginActivity
import com.txtlabs.openeye.utils.SharedPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val time = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Hawk.init(this).build()

        Handler(Looper.getMainLooper()).postDelayed({
            sessionChecker()
        }, time)

        withCoroutine(time)
    }


    private fun sessionChecker() {
        // sesion checeker using firebase and validation
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@MainActivity, NavigationActivity::class.java))
            finishAffinity()
        } else {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun withCoroutine(timeIntent: Long) {
        val mScope = CoroutineScope(Dispatchers.Main)
        mScope.launch {
            delay(timeIntent)
            withContext(Dispatchers.Main) {
                launchPostSplashActivity()
                mScope.cancel(null)
            }
        }
    }

    private fun launchPostSplashActivity() {
        val loginData : LoginResponse? = Hawk.get(SharedPrefs.KEY_LOGIN)
        val intent = if (loginData == null) {
            Intent(this, LoginActivity::class.java)
        }
        else {
            Intent(this, NavigationActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}