package com.think.design.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.think.base.BaseFragment
import com.think.design.R
import com.think.design.databinding.FragmentHomeMainBinding
import kotlin.math.abs

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/12/22 18:20
 * desc   :
 */
class HomeMainFragment : BaseFragment<FragmentHomeMainBinding>() {

    private var adapter : HomeMainAdapter? = null

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeMainBinding {
        return FragmentHomeMainBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initHeader()
        initTopDividerVisibility()
        initRecyclerView()
        initPreferencesButton()
        initSearchButton()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        binding.homeSearchView.setQuery("", true)
    }



    fun initHeader() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View, insetsCompat: WindowInsetsCompat ->
            binding.homeAppBarLayout.setPadding(0, insetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top, 0, 0)
            insetsCompat
        }
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
        val itemSize = resources.getDimensionPixelSize(R.dimen.md_sys_grid_item_height)
        val gridSpanCount = displayWidth / itemSize
        val spanCount = MathUtils.clamp(gridSpanCount, GRID_SPAN_COUNT_MIN, GRID_SPAN_COUNT_MAX)

        binding.homeRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.homeRecyclerView.addItemDecoration(
            HomeItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.md_sys_divider_regular
                ),
                ContextCompat.getColor(requireContext(), R.color.md_sys_grid_divider_color),
                gridSpanCount
            )
        )

        adapter = HomeMainAdapter(requireActivity(), HomeUtil.featureList)
        binding.homeRecyclerView.adapter = adapter
    }

    fun initPreferencesButton() {
        binding.homePreferencesButton.setOnClickListener {

        }
    }

    fun initSearchButton() {
        binding.homeSearchButton.setOnClickListener {
            openSearchView()
        }
    }

    fun initSearchView() {
        binding.homeSearchView.setOnClickListener {
            closeSearchView()
        }
        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

        })
    }

    private fun openSearchView() {
        val openSearchViewTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        openSearchViewTransition.addTarget(binding.homeHeader)
        openSearchViewTransition.addTarget(binding.homeSearchView)
        TransitionManager.beginDelayedTransition(binding.homeHeader, openSearchViewTransition)

        binding.homeHeader.visibility = View.GONE
        binding.homeLogo.visibility = View.GONE
        binding.homeSearchView.visibility = View.VISIBLE
        binding.homeSearchView.requestFocus()
    }

    private fun closeSearchView() {
        val closeSearchViewTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        closeSearchViewTransition.addTarget(binding.homeHeader)
        closeSearchViewTransition.addTarget(binding.homeSearchView)
        TransitionManager.beginDelayedTransition(binding.homeHeader, closeSearchViewTransition)

        binding.homeHeader.visibility = View.VISIBLE
        binding.homeLogo.visibility = View.VISIBLE
        binding.homeSearchView.visibility = View.GONE
        binding.homeSearchView.setQuery("", true)
    }

    companion object {
        const val GRID_SPAN_COUNT_MIN = 1
        const val GRID_SPAN_COUNT_MAX = 4
    }

}