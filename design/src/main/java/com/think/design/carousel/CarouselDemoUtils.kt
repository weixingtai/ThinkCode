package com.think.design.carousel

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import kotlin.math.abs

internal object CarouselDemoUtils {
    @JvmStatic
    fun createUpdateSliderOnScrollListener(
        slider: Slider, adapter: CarouselAdapter
    ): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            private var dragged = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    dragged = true
                } else if (dragged && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.computeHorizontalScrollRange() != 0) {
                        slider.setValue(
                            ((adapter.getItemCount() - 1)
                                    * abs(recyclerView.computeHorizontalScrollOffset())
                                    / recyclerView.computeHorizontalScrollRange()
                                    + 1).toFloat()
                        )
                    }
                    dragged = false
                }
            }
        }
    }

    @JvmStatic
    fun createScrollToPositionSliderTouchListener(
        recyclerView: RecyclerView
    ): Slider.OnSliderTouchListener {
        return object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                recyclerView.smoothScrollToPosition((slider.getValue().toInt()) - 1)
            }
        }
    }
}
