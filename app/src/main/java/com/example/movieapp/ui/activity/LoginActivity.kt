package com.example.movieapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.viewModel.LoginViewModel
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    var userValid: Boolean = false
    private val CACHE_DURATION = 5 * 60 * 1000

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
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                var sharedPreferences: SharedPreferences? =
                    getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                if (sharedPreferences != null) {
                    userViewModel.getSharedPreference(this, sharedPreferences)
                }
                lifecycleScope.launch {
                    userViewModel.loadBookmarks()
                    userViewModel.loadGenres()
                }
            } else {
                Toast.makeText(this, "User Not Found !!!", Toast.LENGTH_SHORT).show()
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
            userValid = true
        }
        if (!userValid) {
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
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}