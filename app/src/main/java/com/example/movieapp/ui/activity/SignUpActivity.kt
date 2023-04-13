package com.example.movieapp.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivitySignupBinding
import com.example.movieapp.model.UserDetails
import com.example.movieapp.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    var checkedButton: RadioButton? = null
    var checkedButtonText: String? = null
    private val loginViewModel: LoginViewModel by viewModels {
        (application as MyApplication).appComponent.viewModelsFactory()
    }
    lateinit var bindingData: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        bindingData = binding
        setContentView(binding.root)
        lifecycleScope.launch {
            loginViewModel.init()
        }

        binding.signupButton.setOnClickListener {
            lifecycleScope.launch {
                registerUser()
            }
        }

        binding.DOB.setOnClickListener {
            setDate()
        }
        binding.male.setOnClickListener {
            checkGenderMale()
        }
        binding.female.setOnClickListener {
            checkGenderFemale()
        }
    }

    private fun checkGenderMale() {
        bindingData.gender.clearCheck()
        bindingData.gender.clearFocus()
        var checkedId = bindingData.male.id
        checkedButtonText = "Male"
        checkedButton = findViewById(checkedId)
        bindingData.female.isChecked = false
        bindingData.male.isChecked = true
    }

    private fun checkGenderFemale() {
        bindingData.gender.clearCheck()
        bindingData.gender.clearFocus()
        var checkedId = bindingData.female.id
        checkedButtonText = "Female"
        checkedButton = findViewById(checkedId)
        bindingData.female.isChecked = true
        bindingData.male.isChecked = false
    }

    private suspend fun registerUser() {
        if (checkUser()) {
            var intent: Intent? = null
            val date = bindingData.DOB.text
            val user = UserDetails(
                0,
                bindingData.username.text.toString(),
                bindingData.email.text.toString(),
                bindingData.password.text.toString(),
                date.toString(),
                checkedButtonText.toString()
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

    private fun setDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this, { view, year, monthOfYear, dayOfMonth ->
                val returnDate = "${monthOfYear + 1}-$dayOfMonth-$year"
                var date =
                    SimpleDateFormat("MM-dd-yyyy").parse(returnDate)?.toString()
                date = date?.substring(4, 10) + date?.substring(29, 34)
                bindingData.DOB.setText(date.toString())
                bindingData.DOB.error = null
                Toast.makeText(
                    this,
                    "$date",
                    Toast.LENGTH_LONG
                ).show()
            }, year - 30, month, day
        )
        dpd.show()
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
            if (bindingData.DOB.text.isEmpty()) {
                bindingData.DOB.error = "Invalid Input"
            }
            if (bindingData.gender.isEmpty()) {
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