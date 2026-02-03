package com.think.design.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.think.design.databinding.FragmentBaseListingBinding
import com.think.design.databinding.FragmentBaseListingItemBinding
import com.think.design.feature.Example

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/2 17:03
 * desc   :
 */
abstract class BaseListingFragment : Fragment() {

    @StringRes
    open fun getTitleResId(): Int { return 0 }

    @StringRes
    open fun getDescResId(): Int { return 0 }

    protected abstract fun onCreateMainExample(): Example

    protected abstract fun onCreateExtraList(): List<Example>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View? {
        val binding = FragmentBaseListingBinding.inflate(inflater)
        if (getTitleResId() != 0) {
            binding.toolbar.setTitle(getTitleResId())
        } else {
            binding.toolbar.setTitle("")
        }
        if (getTitleResId() != 0) {
            binding.listingMainDescription.setText(getDescResId())
        } else {
            binding.listingMainDescription.text = ""
        }
        binding.listingMainContainer.removeAllViews()
        binding.listingExtraContainer.removeAllViews()

        val mainExample = onCreateMainExample()

        val mainBinding = FragmentBaseListingItemBinding.inflate(inflater)
        mainBinding.listingItemContainer.setOnClickListener {
            findNavController().navigate(mainExample.buildDestinationId())
        }
        mainBinding.listingItemTitle.setText(mainExample.buildTitleResId())
        mainBinding.listingItemDesc.text = mainExample.buildClassName()

        binding.listingMainContainer.addView(mainBinding.root)

        val extrasExampleList = onCreateExtraList()
        if (extrasExampleList.isNotEmpty()) {
            binding.listingExtraSection.visibility = View.VISIBLE
        } else {
            binding.listingExtraSection.visibility = View.GONE
        }
        for (extrasExample in extrasExampleList) {
            val extraBinding = FragmentBaseListingItemBinding.inflate(inflater)
            extraBinding.listingItemContainer.setOnClickListener {

            }
            extraBinding.listingItemTitle.setText(extrasExample.buildTitleResId())
            extraBinding.listingItemDesc.text = extrasExample.buildClassName()

            binding.listingExtraContainer.addView(extraBinding.root)
        }

        return binding.root
    }

}