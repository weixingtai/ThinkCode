package com.think.design.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.UncontainedCarouselStrategy
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.think.design.R
import com.think.design.carousel.CarouselData.createItems
import com.think.design.carousel.CarouselDemoUtils.createScrollToPositionSliderTouchListener
import com.think.design.feature.DemoFragment

class UncontainedCarouselDemoFragment : DemoFragment() {
    private var horizontalDivider: MaterialDividerItemDecoration? = null
    private var adapter: CarouselAdapter? = null
    private var positionSlider: Slider? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        return layoutInflater.inflate(
            R.layout.cat_carousel_uncontained_fragment, viewGroup, false /* attachToRoot */
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
        val snapSwitch = view.findViewById<MaterialSwitch>(R.id.snap_switch)
        val itemCountDropdown = view.findViewById<AutoCompleteTextView>(R.id.item_count_dropdown)
        positionSlider = view.findViewById<Slider>(R.id.position_slider)

        val uncontainedRecyclerView =
            view.findViewById<RecyclerView>(R.id.uncontained_carousel_recycler_view)
        val uncontainedCarouselLayoutManager =
            CarouselLayoutManager(UncontainedCarouselStrategy())
        uncontainedCarouselLayoutManager.setDebuggingEnabled(
            uncontainedRecyclerView, debugSwitch.isChecked()
        )
        uncontainedRecyclerView.setLayoutManager(uncontainedCarouselLayoutManager)
        uncontainedRecyclerView.setNestedScrollingEnabled(false)

        debugSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                uncontainedRecyclerView.setBackgroundResource(
                    if (isChecked) R.drawable.dashed_outline_rectangle else 0
                )
                uncontainedCarouselLayoutManager.setDebuggingEnabled(
                    uncontainedRecyclerView,
                    isChecked
                )
            })

        drawDividers.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    uncontainedRecyclerView.addItemDecoration(horizontalDivider!!)
                } else {
                    uncontainedRecyclerView.removeItemDecoration(horizontalDivider!!)
                }
            })

        val snapHelper = CarouselSnapHelper()
        snapSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    snapHelper.attachToRecyclerView(uncontainedRecyclerView)
                } else {
                    snapHelper.attachToRecyclerView(null)
                }
            })

        adapter =
            CarouselAdapter(
                CarouselItemListener { item: CarouselItem?, position: Int ->
                    uncontainedRecyclerView.scrollToPosition(position)
                    positionSlider!!.setValue((position + 1).toFloat())
                },
                R.layout.cat_carousel_item_narrow
            )
        uncontainedRecyclerView.addOnScrollListener(
            CarouselDemoUtils.createUpdateSliderOnScrollListener(positionSlider!!, adapter!!)
        )

        itemCountDropdown.setOnItemClickListener(
            OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
                adapter!!.submitList(
                    createItems().subList(0, position),
                    Companion.updateSliderRange(positionSlider!!, adapter!!)
                )
            })

        positionSlider!!.addOnSliderTouchListener(
            createScrollToPositionSliderTouchListener(uncontainedRecyclerView)
        )

        uncontainedRecyclerView.setAdapter(adapter)
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
