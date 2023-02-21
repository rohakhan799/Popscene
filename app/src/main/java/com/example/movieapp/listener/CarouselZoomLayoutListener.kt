package com.example.movieapp.listener

import android.support.annotation.NonNull
import android.view.View
import com.mig35.carousellayoutmanager.CarouselLayoutManager


class CarouselZoomLayoutListener {
    private var mScaleMultiplier = 0f

    fun CarouselZoomPostLayoutListener() {
      //  this(0.17f)
    }

    fun CarouselZoomPostLayoutListener(scaleMultiplier: Float) {
        mScaleMultiplier = scaleMultiplier
    }

    fun transformChild(
        @NonNull child: View,
        itemPositionToCenterDiff: Float,
        orientation: Int
    ): ItemTransformation? {
        val scale = 1.0f - mScaleMultiplier * Math.abs(itemPositionToCenterDiff)

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        val translateY: Float
        val translateX: Float
        if (CarouselLayoutManager.VERTICAL == orientation) {
            val translateYGeneral: Float = child.getMeasuredHeight() * (1 - scale) / 2f
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral
            translateX = 0f
        } else {
            val translateXGeneral: Float = child.getMeasuredWidth() * (1 - scale) / 2f
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral
            translateY = 0f
        }
        return ItemTransformation(scale, scale, translateX, translateY)
    }
}