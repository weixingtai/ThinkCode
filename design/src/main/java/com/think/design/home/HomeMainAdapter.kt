package com.think.design.home

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 14:34
 * desc   :
 */
class HomeMainAdapter(val activity: FragmentActivity, val featureList: MutableList<HomeFeature>) :
    RecyclerView.Adapter<HomeViewHolder>(), Filterable {

    private val featureFilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList = mutableListOf<HomeFeature>()
            if (constraint.isEmpty()) {
                filteredList.addAll(featureList)
            } else {
                for (homeFeature in featureList) {
                    if (activity.getString(homeFeature.titleResId).lowercase()
                            .contains(constraint.toString().lowercase())
                    ) {
                        filteredList.add(homeFeature)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, filterResults: FilterResults) {
            featureList.clear()
            featureList.addAll(filterResults.values as MutableList<HomeFeature>)
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): HomeViewHolder {
        return HomeViewHolder(activity, viewGroup)
    }

    override fun onBindViewHolder(homeViewHolder: HomeViewHolder, i: Int) {
        val homeFeature = featureList[i]
        homeViewHolder.titleView.setText(homeFeature.titleResId)
        homeViewHolder.imageView.setImageResource(homeFeature.drawableResId)
        homeViewHolder.itemView.setOnClickListener { _: View? ->
            activity.findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(homeFeature.routeResId)
        }
    }

    override fun getItemCount(): Int {
        return featureList.size
    }

    override fun getFilter(): Filter {
        return featureFilter
    }
}