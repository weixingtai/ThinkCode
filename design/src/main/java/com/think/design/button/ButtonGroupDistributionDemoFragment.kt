package com.think.design.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.feature.DemoFragment

class ButtonGroupDistributionDemoFragment : DemoFragment() {
    /** Create a Demo View with different types of [MaterialButtonGroup].  */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                R.layout.cat_buttons_group_distribution_fragment,
                viewGroup,  /* attachToRoot= */
                false
            )

        return view
    }
}
