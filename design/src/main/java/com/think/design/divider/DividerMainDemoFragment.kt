package com.think.design.divider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.feature.DemoFragment

/** Main demo of the Material divider.  */
class DividerMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.cat_divider_fragment, viewGroup, false)
    }
}
