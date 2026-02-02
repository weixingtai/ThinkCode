package com.think.design.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.think.design.R
import com.think.design.feature.DemoFragment

open class CardMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(this.cardContent, viewGroup, false /* attachToRoot */)

        return view
    }

    @get:LayoutRes
    protected open val cardContent: Int
        get() = R.layout.cat_card_fragment
}
