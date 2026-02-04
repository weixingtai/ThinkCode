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

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/2 17:03
 * desc   :
 */
abstract class BaseListingFragment : Fragment() {

    @StringRes
    open fun getTitleResId(): Int {
        return 0
    }

    @StringRes
    open fun getDescResId(): Int {
        return 0
    }

    protected abstract fun onCreateMainExample(): Example

    protected abstract fun onCreateExtraList(): List<Example>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val binding = FragmentBaseListingBinding.inflate(inflater)
        if (getTitleResId() != 0) {
            binding.baseListingToolbar.setTitle(getTitleResId())
        } else {
            binding.baseListingToolbar.setTitle("")
        }
        if (getTitleResId() != 0) {
            binding.baseListingMainDescription.setText(getDescResId())
        } else {
            binding.baseListingMainDescription.text = ""
        }
        binding.baseListingMainContainer.removeAllViews()
        binding.baseListingExtraContainer.removeAllViews()

        val mainExample = onCreateMainExample()

        val mainBinding = FragmentBaseListingItemBinding.inflate(inflater)
        mainBinding.baseListingItemContainer.setOnClickListener {
            findNavController().navigate(mainExample.buildDestinationId())
        }
        mainBinding.baseListingItemTitle.setText(mainExample.buildTitleResId())
        mainBinding.baseListingItemDesc.text = mainExample.buildClassName()

        binding.baseListingMainContainer.addView(mainBinding.root)

        val extrasExampleList = onCreateExtraList()
        if (extrasExampleList.isNotEmpty()) {
            binding.baseListingExtraSection.visibility = View.VISIBLE
        } else {
            binding.baseListingExtraSection.visibility = View.GONE
        }
        for (extrasExample in extrasExampleList) {
            val extraBinding = FragmentBaseListingItemBinding.inflate(inflater)
            extraBinding.baseListingItemContainer.setOnClickListener {

            }
            extraBinding.baseListingItemTitle.setText(extrasExample.buildTitleResId())
            extraBinding.baseListingItemDesc.text = extrasExample.buildClassName()

            binding.baseListingExtraContainer.addView(extraBinding.root)
        }

        return binding.root
    }

}