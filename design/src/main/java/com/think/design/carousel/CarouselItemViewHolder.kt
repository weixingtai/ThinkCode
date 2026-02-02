package com.think.design.carousel

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.think.design.R

internal class CarouselItemViewHolder(itemView: View, private val listener: CarouselItemListener?) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView

    init {
        imageView = itemView.findViewById<ImageView>(R.id.carousel_image_view)
    }

    fun bind(item: CarouselItem) {
        Glide.with(imageView.getContext()).load(item.drawableRes).centerCrop().into(imageView)
        imageView.setContentDescription(imageView.getResources().getString(item.contentDescRes))
        itemView.setOnClickListener(View.OnClickListener { v: View? ->
            listener?.onItemClicked(
                item,
                getBindingAdapterPosition()
            )
        })
    }
}
