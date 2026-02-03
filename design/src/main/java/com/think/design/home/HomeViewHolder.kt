package com.think.design.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2026/2/3 15:10
 * desc   :
 */
class HomeViewHolder(activity: FragmentActivity, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(activity).inflate(R.layout.cat_toc_item, viewGroup, false)
    ) {
    val titleView = itemView.findViewById<TextView>(R.id.cat_toc_title)
    val imageView = itemView.findViewById<ImageView>(R.id.cat_toc_image)
}
