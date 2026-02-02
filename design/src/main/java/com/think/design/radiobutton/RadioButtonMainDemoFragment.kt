package com.think.design.radiobutton

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.think.design.R
import com.think.design.feature.DemoFragment

class RadioButtonMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(R.layout.cat_radiobutton, viewGroup, false /* attachToRoot */)
        return view
    }
}
