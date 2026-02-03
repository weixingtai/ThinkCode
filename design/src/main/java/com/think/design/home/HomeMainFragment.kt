package com.think.design.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.think.base.BaseFragment
import com.think.design.R
import com.think.design.databinding.FragmentHomeBinding
import kotlin.math.abs

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class HomeMainFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTopDividerVisibility()
        initRecyclerView()
    }

    fun initTopDividerVisibility() {
        binding.homeAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.homeTopDivider.visibility = View.GONE
            } else {
                binding.homeTopDivider.visibility = View.VISIBLE
            }
        }
    }

    fun initRecyclerView() {
        val displayMetrics = resources.displayMetrics
        val displayWidth = displayMetrics.widthPixels
        val itemSize = resources.getDimensionPixelSize(R.dimen.cat_toc_item_size)
        val gridSpanCount = displayWidth / itemSize
        val spanCount = MathUtils.clamp(gridSpanCount, GRID_SPAN_COUNT_MIN, GRID_SPAN_COUNT_MAX)

        binding.homeRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.homeRecyclerView.addItemDecoration(HomeItemDecoration(resources.getDimensionPixelSize(R.dimen.cat_toc_grid_divider_size), ContextCompat.getColor(requireContext(), R.color.cat_toc_grid_divider_color), gridSpanCount))

        val adapter = HomeMainAdapter(requireActivity(), HomeUtil.featureList)
        binding.homeRecyclerView.adapter = adapter
    }

    companion object {
        const val GRID_SPAN_COUNT_MIN = 1
        const val GRID_SPAN_COUNT_MAX = 4
    }

}