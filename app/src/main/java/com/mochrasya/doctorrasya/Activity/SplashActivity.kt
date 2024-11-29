package com.mochrasya.doctorrasya.Activity

import android.content.Intent
import android.os.Bundle
import com.mochrasya.doctorrasya.RegisterActivity
import com.mochrasya.doctorrasya.databinding.ActivitySplashBinding
import com.mochrasya.doctorrasya.login

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            loginBtn.setOnClickListener {
                startActivity(Intent(this@SplashActivity, login::class.java))
            }
            registerBtn.setOnClickListener{
                startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
            }
        }
    }
}