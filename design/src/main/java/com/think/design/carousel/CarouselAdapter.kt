package com.think.design.carousel

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnHoverListener
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.think.design.R

internal class CarouselAdapter @JvmOverloads constructor(
    private val listener: CarouselItemListener?,
    @field:LayoutRes @param:LayoutRes private val itemLayoutRes: Int = R.layout.cat_carousel_item
) : ListAdapter<CarouselItem?, CarouselItemViewHolder?>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): CarouselItemViewHolder {
        return CarouselItemViewHolder(
            LayoutInflater.from(viewGroup.getContext())
                .inflate(itemLayoutRes, viewGroup, false), listener
        )
    }

    override fun onBindViewHolder(carouselItemViewHolder: CarouselItemViewHolder, pos: Int) {
        carouselItemViewHolder.bind(getItem(pos)!!)
        carouselItemViewHolder.itemView.setOnHoverListener(
            OnHoverListener { v: View?, event: MotionEvent? ->
                when (event!!.getAction()) {
                    MotionEvent.ACTION_HOVER_ENTER -> v!!.setAlpha(0.5f)
                    MotionEvent.ACTION_HOVER_EXIT -> v!!.setAlpha(1f)
                    else -> {}
                }
                false
            })
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<CarouselItem?> =
            object : DiffUtil.ItemCallback<CarouselItem?>() {
                override fun areItemsTheSame(
                    oldItem: CarouselItem, newItem: CarouselItem
                ): Boolean {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: CarouselItem, newItem: CarouselItem
                ): Boolean {
                    return false
                }
            }
    }
}
