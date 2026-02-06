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
        add(HomeFeature(R.string.switch_title, R.drawable.ic_feature_switch, R.id.navigation_switch_list))
        add(HomeFeature(R.string.loading_indicator_title, R.drawable.ic_feature_loading_indicator, R.id.navigation_loading_indicator_list))
    }


}