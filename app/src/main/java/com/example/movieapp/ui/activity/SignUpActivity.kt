package com.example.movieapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivitySignupBinding
import com.example.movieapp.model.UserDetails
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels {
        (application as MyApplication).appComponent.viewModelsFactory()
    }
    lateinit var bindingData: ActivitySignupBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        bindingData = binding
        setContentView(binding.root)
        // movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        lifecycleScope.launch {
            loginViewModel.init()
        }
        binding.signupButton.setOnClickListener {
            lifecycleScope.launch {
                registerUser()
            }
        }
    }

    private suspend fun registerUser() {
        if (checkUser()) {
            var intent: Intent? = null
            val user = UserDetails(
                0,
                bindingData.username.text.toString(),
                bindingData.email.text.toString(),
                bindingData.password.text.toString()
            )

            loginViewModel.isLogin(
                bindingData.email.text.toString(),
                bindingData.password.text.toString()
            )
            loginViewModel.userObjLive.observe(this) { userCheck ->
                if (userCheck != null) {
                    Toast.makeText(this, "User Already Exists !!!", Toast.LENGTH_SHORT).show()

                } else {
                    lifecycleScope.launch {
                        loginViewModel.addUser(user)
                    }
                    Toast.makeText(this, "SIGNUP SUCCESSFUL !!!", Toast.LENGTH_SHORT).show()
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun checkUser(): Boolean {
        var flag: Boolean = false
        if (inputCheck() && emailCheck()) {
            flag = true
        } else {
            flag = false
            if (!emailCheck()) {
                bindingData.email.error = "Invalid Input"
            }
            if (bindingData.email.text.isEmpty()) {
                bindingData.email.error = "Invalid Input"
            }
            if (bindingData.password.text.isEmpty()) {
                bindingData.password.error = "Invalid Input"
            }
            if (bindingData.username.text.isEmpty()) {
                bindingData.username.error = "Invalid Input"
            }
        }
        return flag
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