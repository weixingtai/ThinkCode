package com.think.design.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.think.design.R
import com.think.design.carousel.CarouselData.createItems
import com.think.design.feature.DemoFragment

class CarouselMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        return layoutInflater.inflate(R.layout.cat_carousel, viewGroup, false /* attachToRoot */)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        val carouselRecyclerView =
            view.findViewById<RecyclerView>(R.id.carousel_recycler_view)
        val multiBrowseCenteredCarouselLayoutManager = CarouselLayoutManager()
        carouselRecyclerView.setLayoutManager(multiBrowseCenteredCarouselLayoutManager)
        carouselRecyclerView.setNestedScrollingEnabled(false)

        val adapter =
            CarouselAdapter(
                { item: CarouselItem?, position: Int ->
                    carouselRecyclerView.scrollToPosition(
                        position
                    )
                })
        carouselRecyclerView.setAdapter(adapter)

        adapter.submitList(createItems())
    }
}
