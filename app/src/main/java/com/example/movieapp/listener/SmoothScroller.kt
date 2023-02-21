/*
package com.example.movieapp.listener

import android.annotation.SuppressLint
import android.graphics.PointF
import android.support.annotation.NonNull
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mig35.carousellayoutmanager.CarouselLayoutManager


@SuppressLint("NotConstructor")
class SmoothScroller {
    fun SmoothScroller(@NonNull state: RecyclerView.State, position: Int) {
        require(0 <= position) { "position can't be less then 0. position is : $position" }
        require(position < state.getItemCount()) { "position can't be great then adapter items count. position is : $position" }
    }

    fun computeScrollVectorForPosition(
        targetPosition: Int,
        @NonNull carouselLayoutManager: CarouselLayoutManager
    ): PointF? {
        return carouselLayoutManager.computeScrollVectorForPosition(targetPosition)
    }

    fun calculateDyToMakeVisible(
        view: View?,
        @NonNull carouselLayoutManager: CarouselLayoutManager
    ): Int {
        return if (!carouselLayoutManager.canScrollVertically()) {
            0
        } else carouselLayoutManager.getOffsetForCurrentView(view)
    }

    fun calculateDxToMakeVisible(
        view: View?,
        @NonNull carouselLayoutManager: CarouselLayoutManager
    ): Int {
        return if (!carouselLayoutManager.canScrollHorizontally()) {
            0
        } else carouselLayoutManager.getOffsetForCurrentView(view)
    }
}*/
