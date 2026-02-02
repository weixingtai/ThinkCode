package com.think.design.carousel

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.think.design.R
import com.think.design.carousel.CarouselData.createItems
import com.think.design.feature.DemoFragment
import com.think.design.windowpreferences.WindowPreferencesManager

class FullScreenStrategyDemoFragment : DemoFragment() {
    private var verticalDivider: MaterialDividerItemDecoration? = null
    private var bottomSheetDialog: BottomSheetDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        // We want to force portrait mode for the fullscreen vertical carousel
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        return layoutInflater.inflate(
            R.layout.cat_carousel_full_screen_fragment, viewGroup, false /* attachToRoot */
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        bottomSheetDialog = BottomSheetDialog(view.getContext())
        bottomSheetDialog!!.setContentView(R.layout.cat_carousel_bottom_sheet_contents)
        // Opt in to perform swipe to dismiss animation when dismissing bottom sheet dialog.
        bottomSheetDialog!!.setDismissWithAnimation(true)

        WindowPreferencesManager(requireContext())
            .applyEdgeToEdgePreference(bottomSheetDialog!!.getWindow())
        verticalDivider =
            MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL)

        val showBottomSheetButton = view.findViewById<Button>(R.id.show_bottomsheet_button)
        showBottomSheetButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                bottomSheetDialog!!.show()
            }
        })

        val debugSwitch = bottomSheetDialog!!.findViewById<MaterialSwitch?>(R.id.debug_switch)
        val drawDividers =
            bottomSheetDialog!!.findViewById<MaterialSwitch?>(R.id.draw_dividers_switch)
        val enableFlingSwitch =
            bottomSheetDialog!!.findViewById<MaterialSwitch?>(R.id.enable_fling_switch)
        val itemCountDropdown =
            bottomSheetDialog!!.findViewById<AutoCompleteTextView?>(R.id.item_count_dropdown)
        val positionSlider = bottomSheetDialog!!.findViewById<Slider?>(R.id.position_slider)

        // A vertical fullscreen carousel
        val fullscreenRecyclerView =
            view.findViewById<RecyclerView>(R.id.fullscreen_carousel_recycler_view)
        val carouselLayoutManager =
            CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.VERTICAL)
        carouselLayoutManager.setDebuggingEnabled(
            fullscreenRecyclerView, debugSwitch!!.isChecked()
        )
        fullscreenRecyclerView.setLayoutManager(carouselLayoutManager)
        fullscreenRecyclerView.setNestedScrollingEnabled(false)

        debugSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                carouselLayoutManager.setOrientation(CarouselLayoutManager.VERTICAL)
                fullscreenRecyclerView.setBackgroundResource(
                    if (isChecked) R.drawable.dashed_outline_rectangle else 0
                )
                carouselLayoutManager.setDebuggingEnabled(
                    fullscreenRecyclerView, isChecked
                )
            })

        drawDividers!!.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    fullscreenRecyclerView.addItemDecoration(verticalDivider!!)
                } else {
                    fullscreenRecyclerView.removeItemDecoration(verticalDivider!!)
                }
            })

        val adapter =
            CarouselAdapter(
                CarouselItemListener { item: CarouselItem?, position: Int ->
                    fullscreenRecyclerView.scrollToPosition(
                        position
                    )
                },
                R.layout.cat_carousel_item_vertical
            )
        fullscreenRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                private var dragged = false

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        dragged = true
                    } else if (dragged && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (recyclerView.computeVerticalScrollRange() != 0) {
                            positionSlider!!.setValue(
                                ((adapter.getItemCount() - 1)
                                        * recyclerView.computeVerticalScrollOffset()
                                        / recyclerView.computeVerticalScrollRange()
                                        + 1).toFloat()
                            )
                        }
                        dragged = false
                    }
                }
            })

        val flingDisabledSnapHelper: SnapHelper = CarouselSnapHelper()
        val flingEnabledSnapHelper: SnapHelper = CarouselSnapHelper(false)

        flingDisabledSnapHelper.attachToRecyclerView(fullscreenRecyclerView)

        enableFlingSwitch!!.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    flingDisabledSnapHelper.attachToRecyclerView(null)
                    flingEnabledSnapHelper.attachToRecyclerView(fullscreenRecyclerView)
                } else {
                    flingEnabledSnapHelper.attachToRecyclerView(null)
                    flingDisabledSnapHelper.attachToRecyclerView(fullscreenRecyclerView)
                }
            })

        itemCountDropdown!!.setOnItemClickListener(
            OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
                adapter.submitList(
                    createItems().subList(0, position),
                    Companion.updateSliderRange(positionSlider!!, adapter)
                )
            })

        positionSlider!!.addOnSliderTouchListener(
            object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {}

                override fun onStopTrackingTouch(slider: Slider) {
                    fullscreenRecyclerView.smoothScrollToPosition((slider.getValue().toInt()) - 1)
                }
            })

        fullscreenRecyclerView.setAdapter(adapter)
        adapter.submitList(createItems(), Companion.updateSliderRange(positionSlider, adapter))
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
