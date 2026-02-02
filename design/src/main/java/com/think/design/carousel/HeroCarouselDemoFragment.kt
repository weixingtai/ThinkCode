package com.think.design.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.think.design.R
import com.think.design.carousel.CarouselData.createItems
import com.think.design.carousel.CarouselDemoUtils.createScrollToPositionSliderTouchListener
import com.think.design.feature.DemoFragment

class HeroCarouselDemoFragment : DemoFragment() {
    private var horizontalDivider: MaterialDividerItemDecoration? = null
    private var adapter: CarouselAdapter? = null
    private var positionSlider: Slider? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        return layoutInflater.inflate(
            R.layout.cat_carousel_hero_fragment, viewGroup, false /* attachToRoot */
        )
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        horizontalDivider =
            MaterialDividerItemDecoration(
                requireContext(), MaterialDividerItemDecoration.HORIZONTAL
            )

        val debugSwitch = view.findViewById<MaterialSwitch>(R.id.debug_switch)
        val drawDividers = view.findViewById<MaterialSwitch>(R.id.draw_dividers_switch)
        val enableFlingSwitch = view.findViewById<MaterialSwitch>(R.id.enable_fling_switch)
        val itemCountDropdown = view.findViewById<AutoCompleteTextView>(R.id.item_count_dropdown)
        positionSlider = view.findViewById<Slider>(R.id.position_slider)
        val startAlignButton = view.findViewById<RadioButton>(R.id.start_align)
        val centerAlignButton = view.findViewById<RadioButton>(R.id.center_align)

        // A hero carousel
        val heroStartRecyclerView =
            view.findViewById<RecyclerView>(R.id.hero_start_carousel_recycler_view)
        val heroStartCarouselLayoutManager =
            CarouselLayoutManager(HeroCarouselStrategy())
        heroStartCarouselLayoutManager.setDebuggingEnabled(
            heroStartRecyclerView, debugSwitch.isChecked()
        )
        heroStartRecyclerView.setLayoutManager(heroStartCarouselLayoutManager)
        heroStartRecyclerView.setNestedScrollingEnabled(false)

        debugSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                heroStartRecyclerView.setBackgroundResource(
                    if (isChecked) R.drawable.dashed_outline_rectangle else 0
                )
                heroStartCarouselLayoutManager.setDebuggingEnabled(heroStartRecyclerView, isChecked)
            })

        drawDividers.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    heroStartRecyclerView.addItemDecoration(horizontalDivider!!)
                } else {
                    heroStartRecyclerView.removeItemDecoration(horizontalDivider!!)
                }
            })

        adapter =
            CarouselAdapter(
                CarouselItemListener { item: CarouselItem?, position: Int ->
                    heroStartRecyclerView.scrollToPosition(position)
                    positionSlider!!.setValue((position + 1).toFloat())
                },
                R.layout.cat_carousel_item
            )
        heroStartRecyclerView.addOnScrollListener(
            CarouselDemoUtils.createUpdateSliderOnScrollListener(positionSlider!!, adapter!!)
        )

        val disableFlingSnapHelper: SnapHelper = CarouselSnapHelper()
        val enableFlingSnapHelper: SnapHelper = CarouselSnapHelper(false)

        if (enableFlingSwitch.isChecked()) {
            enableFlingSnapHelper.attachToRecyclerView(heroStartRecyclerView)
        } else {
            disableFlingSnapHelper.attachToRecyclerView(heroStartRecyclerView)
        }

        enableFlingSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    disableFlingSnapHelper.attachToRecyclerView(null)
                    enableFlingSnapHelper.attachToRecyclerView(heroStartRecyclerView)
                } else {
                    enableFlingSnapHelper.attachToRecyclerView(null)
                    disableFlingSnapHelper.attachToRecyclerView(heroStartRecyclerView)
                }
            })

        itemCountDropdown.setOnItemClickListener(
            OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
                adapter!!.submitList(
                    createItems().subList(0, position),
                    Companion.updateSliderRange(positionSlider!!, adapter!!)
                )
            })

        positionSlider!!.addOnSliderTouchListener(
            createScrollToPositionSliderTouchListener(heroStartRecyclerView)
        )

        startAlignButton.setOnClickListener(
            View.OnClickListener { v: View? ->
                heroStartCarouselLayoutManager.setCarouselAlignment(
                    CarouselLayoutManager.ALIGNMENT_START
                )
            })
        centerAlignButton.setOnClickListener(
            View.OnClickListener { v: View? ->
                heroStartCarouselLayoutManager.setCarouselAlignment(
                    CarouselLayoutManager.ALIGNMENT_CENTER
                )
            })

        heroStartRecyclerView.setAdapter(adapter)
    }

    override fun onStart() {
        super.onStart()
        adapter!!.submitList(
            createItems(),
            Companion.updateSliderRange(positionSlider!!, adapter!!)
        )
    }

    companion object {
        private fun updateSliderRange(slider: Slider, adapter: CarouselAdapter): Runnable {
            return Runnable {
                if (adapter.getItemCount() <= 1) {
                    slider.setEnabled(false)
                    return@Runnable
                }
                slider.setValueFrom(1f)
                slider.setValue(1f)
                slider.setValueTo(adapter.getItemCount().toFloat())
                slider.setEnabled(true)
            }
        }
    }
}
