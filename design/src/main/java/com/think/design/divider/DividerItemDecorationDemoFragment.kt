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
import com.think.design.feature.DemoFragment

/** Demo of the MaterialDividerItemDecoration.  */
class DividerItemDecorationDemoFragment : DemoFragment() {

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(
            R.layout.cat_divider_recyclerview_fragment, viewGroup,  /* attachToRoot */false
        )

        val recyclerViewHorizontal =
            view.findViewById<RecyclerView>(R.id.divider_recyclerview_horizontal)
        val recyclerViewVertical =
            view.findViewById<RecyclerView>(R.id.divider_recyclerview_vertical)

        setUpDividers(recyclerViewHorizontal, LinearLayoutManager.HORIZONTAL)
        setUpDividers(recyclerViewVertical, LinearLayoutManager.VERTICAL)

        return view
    }

    private fun setUpDividers(recyclerView: RecyclerView, orientation: Int) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            context, orientation, false
        )
        recyclerView.setLayoutManager(layoutManager)

        val divider = MaterialDividerItemDecoration(requireActivity(), orientation)
        recyclerView.addItemDecoration(divider)

        val adapter = DividerAdapter()
        recyclerView.setAdapter(adapter)
    }

    /** A RecyclerView adapter.  */
    private class DividerAdapter

        : RecyclerView.Adapter<DividerAdapter.MyViewHolder?>() {
        /** Provide a reference to the views for each data item.  */
        private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var item: TextView = itemView as TextView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cat_divider_recyclerview_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.item.text =
                holder.item.resources.getString(R.string.cat_divider_item_text, position + 1)
        }

        override fun getItemCount(): Int {
            return ITEM_COUNT
        }

        companion object {
            private const val ITEM_COUNT = 20
        }
    }
}
