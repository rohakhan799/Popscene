package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Welcome::class.java)
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