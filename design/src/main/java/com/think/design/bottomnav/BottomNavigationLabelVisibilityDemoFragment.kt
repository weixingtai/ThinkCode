package com.think.design.bottomnav

import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.think.design.R

class BottomNavigationLabelVisibilityDemoFragment : BottomNavigationDemoFragment() {
    override fun initBottomNavDemoControls(view: View) {
        super.initBottomNavDemoControls(view)
        initLabelVisibilityModeButtons(view)
        initIconSlider(view)
    }

    override val bottomNavDemoControlsLayout: Int
        get() = R.layout.cat_bottom_navs_label_visibility_controls

    private fun setAllBottomNavsLabelVisibilityMode(@LabelVisibilityMode labelVisibilityMode: Int) {
        for (bn in bottomNavigationViews!!) {
            setBottomNavsLabelVisibilityMode(bn, labelVisibilityMode)
        }
    }

    private fun setBottomNavsLabelVisibilityMode(
        bn: BottomNavigationView, @LabelVisibilityMode labelVisibilityMode: Int
    ) {
        bn.setLabelVisibilityMode(labelVisibilityMode)
    }

    private fun setAllBottomNavsIconSize(size: Int) {
        for (bn in bottomNavigationViews!!) {
            bn.setItemIconSize(size)
        }
    }

    private fun initLabelVisibilityModeButtons(view: View) {
        initLabelVisibilityModeButton(
            view.findViewById<Button?>(R.id.label_mode_auto_button)!!,
            LabelVisibilityMode.LABEL_VISIBILITY_AUTO
        )
        initLabelVisibilityModeButton(
            view.findViewById<Button?>(R.id.label_mode_selected_button)!!,
            LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        )
        initLabelVisibilityModeButton(
            view.findViewById<Button?>(R.id.label_mode_labeled_button)!!,
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        )
        initLabelVisibilityModeButton(
            view.findViewById<Button?>(R.id.label_mode_unlabeled_button)!!,
            LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
        )
    }

    private fun initLabelVisibilityModeButton(
        labelVisibilityModeButton: Button, @LabelVisibilityMode labelVisibilityMode: Int
    ) {
        labelVisibilityModeButton.setOnClickListener(
            View.OnClickListener { v: View? ->
                setAllBottomNavsLabelVisibilityMode(
                    labelVisibilityMode
                )
            })
    }

    private fun initIconSlider(view: View) {
        val iconSizeSlider = view.findViewById<SeekBar>(R.id.icon_size_slider)
        val displayMetrics = getResources().getDisplayMetrics()
        val iconSizeTextView = view.findViewById<TextView>(R.id.icon_size_text_view)
        val iconSizeUnit = "dp"

        iconSizeSlider.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    setAllBottomNavsIconSize(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, progress.toFloat(), displayMetrics
                        ).toInt()
                    )
                    iconSizeTextView.setText(progress.toString() + iconSizeUnit)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
    }
}
