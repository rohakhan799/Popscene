package com.example.movieapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.viewModel.LoginViewModel
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private val userViewModel: LoginViewModel by viewModels {
        (application as MyApplication).appComponent.viewModelsFactory()
    }
    lateinit var bindingData: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        bindingData = binding
        setContentView(binding.root)
        var intent: Intent?
        (application as MyApplication).appComponent.inject(this)
        lifecycleScope.launch {
            userViewModel.init()
        }

        binding.loginButton.setOnClickListener {
            checkUser()
        }

        binding.signupButton.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        userViewModel.userObjLive.observe(this) { user ->
            if (user != null) {
                var sharedPreferences: SharedPreferences? = null
                sharedPreferences = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                userViewModel.getSharedPreference(this, sharedPreferences)
                lifecycleScope.launch {
                    userViewModel.loadBookmarks()
                    userViewModel.loadGenres()
                }
            }
        }
    }

    private fun checkUser() {
        var inputError = false
        if (!emailCheck()) {
            inputError = true
            bindingData.email.error = "Invalid Input"
        }
        if (bindingData.email.text.isEmpty()) {
            inputError = true
            bindingData.email.error = "Invalid Input"
        }
        if (bindingData.password.text.isEmpty()) {
            inputError = true
            bindingData.password.error = "Invalid Input"
        }
        if (!inputError) {
            userViewModel.isLogin(
                bindingData.email.text.toString(),
                bindingData.password.text.toString()
            )
        }
    }

    private fun inputCheck(): Boolean {
        return !(TextUtils.isEmpty(bindingData.email.text) && TextUtils.isEmpty(bindingData.password.text))
    }

    private fun emailCheck(): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
        return emailRegex.matches(bindingData.email.text.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}