package com.think.design.loadingindicator

import androidx.fragment.app.Fragment
import com.think.design.R
import com.think.design.base.BaseListingFragment
import com.think.design.base.Example

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 10:23
 * desc   :
 */
class LoadingIndicatorListFragment : BaseListingFragment() {

    override fun getTitleResId(): Int {
        return R.string.loading_indicator_title
    }

    override fun getDescResId(): Int {
        return R.string.loading_indicator_description
    }

    override fun onCreateMainExample(): Example {
        return object : Example() {
            override fun buildDestinationId(): Int {
                return R.id.navigation_loading_indicator_main
            }

            override fun buildFragment(): Fragment {
                return LoadingIndicatorMainFragment()
            }
        }
    }

    override fun onCreateExtraList(): List<Example> {
        val extrasList = mutableListOf<Example>()
        return extrasList
    }
}