package com.think.design.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils

class ButtonToggleGroupDemoFragment : DemoFragment() {
    private var defaultInset = 0

    /**
     * Create a Demo View with different types of [MaterialButtonToggleGroup] and a switch to
     * toggle [MaterialButtonToggleGroup.setSelectionRequired]
     */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                this.buttonToggleGroupContent,
                viewGroup,  /* attachToRoot= */
                false
            )
        val requireSelectionToggle = view.findViewById<MaterialSwitch>(R.id.switch_toggle)
        defaultInset =
            getResources().getDimensionPixelSize(com.google.android.material.R.dimen.mtrl_btn_inset)
        val buttons: MutableList<MaterialButton> =
            DemoUtils.findViewsWithType<MaterialButton>(view, MaterialButton::class.java)
        val toggleGroups: MutableList<MaterialButtonToggleGroup> =
            DemoUtils.findViewsWithType<MaterialButtonToggleGroup>(
                view,
                MaterialButtonToggleGroup::class.java
            )
        requireSelectionToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (toggleGroup in toggleGroups) {
                    toggleGroup.setSelectionRequired(isChecked)
                }
            })

        val verticalOrientationToggle =
            view.findViewById<MaterialSwitch>(R.id.orientation_switch_toggle)
        verticalOrientationToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (toggleGroup in toggleGroups) {
                    val orientation =
                        if (isChecked) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
                    toggleGroup.setOrientation(orientation)
                    for (i in 0..<toggleGroup.getChildCount()) {
                        val inset = getInsetForOrientation(orientation)
                        val button = toggleGroup.getChildAt(i) as MaterialButton
                        button.setInsetBottom(inset)
                        button.setInsetTop(inset)
                        adjustParams(button.getLayoutParams(), orientation)
                    }

                    toggleGroup.requestLayout()
                }
            })

        val groupEnabledToggle = view.findViewById<MaterialSwitch>(R.id.switch_enable)
        groupEnabledToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (toggleGroup in toggleGroups) {
                    // Enable the button group if enable toggle is checked.
                    toggleGroup.setEnabled(isChecked)
                }
            })

        for (toggleGroup in toggleGroups) {
            toggleGroup.addOnButtonCheckedListener(
                OnButtonCheckedListener { group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean ->
                    val message = "button" + (if (isChecked) " checked" else " unchecked")
                    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
                })
        }

        val innerCornerSizeSlider = view.findViewById<Slider>(R.id.innerCornerSizeSlider)
        innerCornerSizeSlider.addOnChangeListener(
            Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
                for (toggleGroup in toggleGroups) {
                    toggleGroup.setInnerCornerSize(RelativeCornerSize(value / 100f))
                }
            })

        val spacingSlider = view.findViewById<Slider>(R.id.spacingSlider)
        spacingSlider.addOnChangeListener(
            Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
                val pixelsInDp = view.getResources().getDisplayMetrics().density
                for (toggleGroup in toggleGroups) {
                    toggleGroup.setSpacing((value * pixelsInDp).toInt())
                }
            })
        val opticalCenterSwitch = view.findViewById<MaterialSwitch>(R.id.switch_optical_center)
        opticalCenterSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (button in buttons) {
                    button.setOpticalCenterEnabled(isChecked)
                }
            })
        return view
    }

    private fun getInsetForOrientation(orientation: Int): Int {
        return if (orientation == LinearLayout.VERTICAL) 0 else defaultInset
    }

    @get:LayoutRes
    protected val buttonToggleGroupContent: Int
        get() = R.layout.cat_buttons_toggle_group_fragment

    companion object {
        private fun adjustParams(layoutParams: ViewGroup.LayoutParams, orientation: Int) {
            layoutParams.width =
                if (orientation == LinearLayout.VERTICAL) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
}
