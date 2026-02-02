package com.think.design.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonGroup
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils

class ButtonGroupDemoFragment : DemoFragment() {
    /** Create a Demo View with different types of [MaterialButtonGroup].  */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                R.layout.cat_buttons_group_fragment, viewGroup,  /* attachToRoot= */false
            )

        val content = view.findViewById<ViewGroup?>(R.id.button_group_content)
        layoutInflater.inflate(this.iconOnlyButtonGroupContent, content,  /* attachToRoot= */true)
        layoutInflater.inflate(this.labelOnlyButtonGroupContent, content,  /* attachToRoot= */true)
        layoutInflater.inflate(this.mixedButtonGroupContent, content,  /* attachToRoot= */true)

        val buttons: MutableList<MaterialButton> =
            DemoUtils.findViewsWithType<MaterialButton>(view, MaterialButton::class.java)
        val buttonGroups: MutableList<MaterialButtonGroup> =
            DemoUtils.findViewsWithType<MaterialButtonGroup>(view, MaterialButtonGroup::class.java)

        val verticalOrientationToggle =
            view.findViewById<MaterialSwitch>(R.id.orientation_switch_toggle)
        verticalOrientationToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (buttonGroup in buttonGroups) {
                    val orientation =
                        if (isChecked) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
                    buttonGroup.setOrientation(orientation)
                    buttonGroup.requestLayout()
                }
            })
        val groupToggleableToggle = view.findViewById<MaterialSwitch>(R.id.switch_toggle)
        groupToggleableToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (button in buttons) {
                    if (button.getTag() !== MaterialButtonGroup.OVERFLOW_BUTTON_TAG) {
                        button.setCheckable(isChecked)
                        button.refreshDrawableState()
                    }
                }
            })
        val groupEnabledToggle = view.findViewById<MaterialSwitch>(R.id.switch_enable)
        groupEnabledToggle.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (buttonGroup in buttonGroups) {
                    // Enable the button group if enable toggle is checked.
                    buttonGroup.setEnabled(isChecked)
                }
            })
        val opticalCenterSwitch = view.findViewById<MaterialSwitch>(R.id.switch_optical_center)
        opticalCenterSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                for (button in buttons) {
                    button.setOpticalCenterEnabled(isChecked)
                }
            })
        for (button in buttons) {
            if (button.getTag() !== MaterialButtonGroup.OVERFLOW_BUTTON_TAG) {
                val buttonGroup = button.getParent() as MaterialButtonGroup
                button.setOnClickListener(
                    View.OnClickListener { v: View? ->
                        var snackbarText = button.getText().toString() + ""
                        if (snackbarText.isEmpty()) {
                            snackbarText = button.getContentDescription().toString() + ""
                        }
                        snackbarText += " button clicked."
                        Snackbar.make(buttonGroup, snackbarText, Snackbar.LENGTH_LONG).show()
                    })
            }
        }
        return view
    }

    @get:LayoutRes
    protected val iconOnlyButtonGroupContent: Int
        get() = R.layout.cat_button_group_content_icon_only

    @get:LayoutRes
    protected val labelOnlyButtonGroupContent: Int
        get() = R.layout.cat_button_group_content_label_only

    @get:LayoutRes
    protected val mixedButtonGroupContent: Int
        get() = R.layout.cat_button_group_content_mixed
}
