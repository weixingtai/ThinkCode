package com.think.design.home

import com.think.design.R

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 15:23
 * desc   :
 */
object HomeUtil {

    val featureList = mutableListOf<HomeFeature>().apply {
        add(HomeFeature(R.string.divider_title, R.drawable.ic_feature_divider, R.id.navigation_divider_list))
        add(HomeFeature(R.string.checkbox_title, R.drawable.ic_feature_checkbox, R.id.navigation_checkbox_list))
        add(HomeFeature(R.string.radiobutton_title, R.drawable.ic_feature_radiobutton, R.id.navigation_radiobutton_list))
    }


}