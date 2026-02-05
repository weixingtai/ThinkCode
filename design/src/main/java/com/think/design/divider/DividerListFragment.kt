package com.think.design.divider

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
class DividerListFragment : BaseListingFragment() {

    override fun getTitleResId(): Int {
        return R.string.divider_title
    }

    override fun getDescResId(): Int {
        return R.string.divider_description
    }

    override fun onCreateMainExample(): Example {
        return object : Example() {
            override fun buildDestinationId(): Int {
                return R.id.navigation_divider_main
            }

            override fun buildFragment(): Fragment {
                return DividerMainFragment()
            }
        }
    }

    override fun onCreateExtraList(): List<Example> {
        val extrasList = mutableListOf<Example>()
        extrasList.add(object : Example() {
            override fun buildTitleResId(): Int {
                return R.string.divider_item_decoration_demo_title
            }
            override fun buildDestinationId(): Int {
                return R.id.navigation_divider_decoration
            }
            override fun buildFragment(): Fragment {
                return DividerDecorationFragment()
            }
        })
        return extrasList
    }
}