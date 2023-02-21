/*
package com.example.movieapp.listener

import android.support.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView

import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager


class CenterScrollListener : RecyclerView.OnScrollListener() {
    private var mAutoSet = true
    override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager !is CarouselLayoutManager) {
            mAutoSet = true
            return
        }
        val lm = layoutManager
        if (!mAutoSet) {
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                val scrollNeeded: Int = lm.getOffsetCenterView()
                if (HORIZONTAL === lm.getOrientation()) {
                    recyclerView.smoothScrollBy(scrollNeeded, 0)
                } else {
                    recyclerView.smoothScrollBy(0, scrollNeeded)
                }
                mAutoSet = true
            }
        }
        if (RecyclerView.SCROLL_STATE_DRAGGING == newState || RecyclerView.SCROLL_STATE_SETTLING == newState) {
            mAutoSet = false
        }
    }
}*/
