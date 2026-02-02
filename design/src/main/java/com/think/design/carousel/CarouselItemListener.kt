package com.think.design.carousel

internal fun interface CarouselItemListener {
    fun onItemClicked(item: CarouselItem?, position: Int)
}
