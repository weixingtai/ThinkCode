package com.think.design.loadingindicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.loadingindicator.LoadingIndicatorDrawable
import com.google.android.material.loadingindicator.LoadingIndicatorSpec
import com.think.design.R
import com.think.design.feature.DemoFragment

class LoadingIndicatorMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(
            R.layout.cat_loading_indicator_fragment, viewGroup, false /* attachToRoot */
        )

        val spec = LoadingIndicatorSpec(
            requireContext(),
            null,
            0,
            com.google.android.material.R.style.Widget_Material3_LoadingIndicator
        )
        spec.setScaleToFit(true)
        val loadingIndicatorDrawable = LoadingIndicatorDrawable.create(requireContext(), spec)
        val loadingButton = view.findViewById<MaterialButton>(R.id.loading_btn)
        loadingButton.setIcon(loadingIndicatorDrawable)

        return view
    }
}
