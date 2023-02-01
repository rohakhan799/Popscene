package com.example.movieapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import java.util.*


class ViewPagerAdapter(
    val context: Context,
    val imageList: List<Int>,
    val mainList: List<String>,
    val subList: List<String>
) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val myLayoutInf =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = myLayoutInf.inflate(R.layout.slider_item, container, false)
        val imageView: ImageView =
            itemView.findViewById<View>(R.id.imageView_sliderImg) as ImageView
        val mainTView: TextView = itemView.findViewById(R.id.textView_mainhead)
        val subTView: TextView = itemView.findViewById(R.id.textView_subhead)

        imageView.setImageResource(imageList.get(position))
        mainTView.setText(mainList[position])
        subTView.setText(subList[position])

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as ConstraintLayout)
    }

}

