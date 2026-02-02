package com.think.design.button

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.feature.DemoUtils
import kotlin.math.max

class ButtonsMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(this.buttonsContent, viewGroup, false /* attachToRoot */)

        val labelButtonContent = view.findViewById<ViewGroup?>(R.id.labelButtonContent)
        View.inflate(getContext(), this.labelButtonContent, labelButtonContent)
        val labelIconButtonContent = view.findViewById<ViewGroup?>(R.id.labelIconButtonContent)
        View.inflate(getContext(), this.labelIconButtonContent, labelIconButtonContent)
        val iconButtonContent = view.findViewById<ViewGroup?>(R.id.iconButtonContent)
        View.inflate(getContext(), this.iconButtonContent, iconButtonContent)

        val buttons: MutableList<MaterialButton> =
            DemoUtils.findViewsWithType<MaterialButton>(view, MaterialButton::class.java)
        var maxMeasuredWidth = 0
        val displayMetrics = getResources().getDisplayMetrics()

        for (button in buttons) {
            button.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
            maxMeasuredWidth = max(maxMeasuredWidth, button.getMeasuredWidth())
            button.setOnClickListener(
                View.OnClickListener { v: View? ->
                    // Show a Snackbar with an action button, which should also have a MaterialButton style
                    val snackbar =
                        Snackbar.make(v!!, R.string.cat_button_clicked, Snackbar.LENGTH_LONG)
                    snackbar.setAction(
                        R.string.cat_snackbar_action_button_text,
                        View.OnClickListener { v1: View? -> })
                    snackbar.show()
                })
        }

        // Using SwitchCompat here to avoid class cast issues in derived demos.
        val enabledSwitch = view.findViewById<SwitchCompat>(R.id.cat_button_enabled_switch)
        enabledSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                val updatedText =
                    getText(
                        if (isChecked)
                            R.string.cat_button_label_enabled
                        else
                            R.string.cat_button_label_disabled
                    )
                for (button in buttons) {
                    if (!TextUtils.isEmpty(button.getText())) {
                        // Do not update icon only button.
                        button.setText(updatedText)
                    }
                    button.setEnabled(isChecked)
                    button.setFocusable(isChecked)
                }
            })

        return view
    }

    @get:LayoutRes
    protected val labelButtonContent: Int
        get() = R.layout.cat_label_buttons_content

    @get:LayoutRes
    protected val labelIconButtonContent: Int
        get() = R.layout.cat_label_icon_buttons_content

    @get:LayoutRes
    protected val iconButtonContent: Int
        get() = R.layout.cat_icon_buttons_content

    @get:LayoutRes
    protected val buttonsContent: Int
        get() = R.layout.cat_buttons_fragment
}
