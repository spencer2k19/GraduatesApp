package com.example.graduatesapp.ui.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.graduatesapp.R
import com.example.graduatesapp.ui.presentation.auth.AuthActivity
import com.example.graduatesapp.ui.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val introViewModel:IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            introViewModel.startup()
        },3000L)

        introViewModel.isLogged.observe(this, Observer {
            if(it) {
                startActivity(Intent(this,HomeActivity::class.java))
            } else {
                startActivity(Intent(this,AuthActivity::class.java))
            }
            finish()
        })

    }
}