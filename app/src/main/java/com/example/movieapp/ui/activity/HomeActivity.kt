package com.example.movieapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.ui.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val discoverFragment = DiscoverFragment()
        val playedFragment = PlayedFragment()
        val accountFragment = AccountFragment()
        val bookmarkFragment = BookmarkFragment()

        setCurrentFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeFragment())
                R.id.discover -> setCurrentFragment(discoverFragment)
                R.id.played -> setCurrentFragment(playedFragment)
                R.id.account -> setCurrentFragment(accountFragment)
                R.id.bookmark -> setCurrentFragment(bookmarkFragment)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}