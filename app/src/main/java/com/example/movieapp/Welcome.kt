package com.example.movieapp

import adapter.CustomViewPager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivityWelcomeBinding
import data.Datasource

class Welcome : AppCompatActivity() {
    lateinit var viewPager: CustomViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = Datasource();
        viewPager = findViewById(R.id.idViewPager)
        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.imageryscene
        imageList = imageList + R.drawable.imageryscene2
        imageList = imageList + R.drawable.imageryscene3

        viewPagerAdapter = ViewPagerAdapter(this@Welcome, imageList, data.mainList, data.sublist)
        viewPager.adapter = viewPagerAdapter
        viewPager.setSwipePagingEnabled(false)

    }

    private fun getItem(i: Int): Int {
        return viewPager.getCurrentItem() + i
    }

    fun jumpToPage(view: View?) {
        val count = getItem(+1)
        if (count === 3) {
            val intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            viewPager.setCurrentItem(getItem(+1), true);
        }

    }
}