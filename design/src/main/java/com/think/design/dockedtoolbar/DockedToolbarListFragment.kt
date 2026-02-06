package com.think.design.dockedtoolbar

import androidx.fragment.app.Fragment
import com.think.design.R
import com.think.design.base.BaseListingFragment
import com.think.design.base.Example
import com.think.design.checkbox.CheckBoxMainFragment

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 10:23
 * desc   :
 */
class DockedToolbarListFragment : BaseListingFragment() {

    override fun getTitleResId(): Int {
        return R.string.checkbox_title
    }

    override fun getDescResId(): Int {
        return R.string.checkbox_description
    }

    override fun onCreateMainExample(): Example {
        return object : Example() {
            override fun buildDestinationId(): Int {
                return R.id.navigation_checkbox_main
            }

            override fun buildFragment(): Fragment {
                return CheckBoxMainFragment()
            }
        }
    }

    override fun onCreateExtraList(): List<Example> {
        val extrasList = mutableListOf<Example>()
        return extrasList
    }
}