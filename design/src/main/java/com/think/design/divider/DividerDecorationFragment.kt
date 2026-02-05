package com.think.design.divider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.think.design.R
import com.think.design.base.BaseLandingFragment
import com.think.design.databinding.FragmentDividerDecorationBinding

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/4 16:11
 * desc   :
 */
class DividerDecorationFragment : BaseLandingFragment<FragmentDividerDecorationBinding>() {

    override fun getTitleResId(): Int {
        return R.string.divider_title
    }

    override fun onCreateLandingView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ) = FragmentDividerDecorationBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDividers(binding.dividerRecyclerviewHorizontal, LinearLayoutManager.HORIZONTAL)
        initDividers(binding.dividerRecyclerviewVertical, LinearLayoutManager.VERTICAL)
    }

    fun initDividers(recyclerView: RecyclerView, orientation: Int) {
        val layoutManager = LinearLayoutManager(requireContext(), orientation, false)
        recyclerView.setLayoutManager(layoutManager)
        val divider = MaterialDividerItemDecoration(requireContext(), orientation)
        recyclerView.addItemDecoration(divider)
        val adapter = DividerAdapter()
        recyclerView.setAdapter(adapter)
    }

    class DividerAdapter : RecyclerView.Adapter<DividerAdapter.DividerViewHolder>() {

        class DividerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var item: TextView = itemView as TextView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DividerViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_divider_item, parent, false)
            return DividerViewHolder(view)
        }

        override fun onBindViewHolder(holder: DividerViewHolder, position: Int) {
            holder.item.text = holder.item.resources.getString(R.string.divider_item_text, position + 1)
        }

        override fun getItemCount(): Int {
            return ITEM_COUNT
        }

        companion object {
            private const val ITEM_COUNT = 20
        }

    }

}