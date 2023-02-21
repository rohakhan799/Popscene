package com.example.movieapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent: Intent? = null
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        Log.d("TestLog", sharedPreferences?.getBoolean("firstrun", true).toString())
        if (sharedPreferences?.getBoolean("firstrun", true) == true) {
            sharedPreferences?.edit()?.putBoolean("firstrun", false)?.apply()
            intent = Intent(this, Welcome::class.java)
        } else {
            intent=Intent(this, HomeActivity::class.java)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 3000)

        /**binding.button.setOnClickListener {
        val intent: Intent = Intent(this, Welcome::class.java)
        startActivity(intent)
        }

        binding.button2.setOnClickListener {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, Movie1())
        .commit()
        }*/
    }
}