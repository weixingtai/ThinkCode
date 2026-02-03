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
        LayoutInflater.from(activity).inflate(R.layout.fragment_home_item, viewGroup, false)
    ) {
    val titleView: TextView = itemView.findViewById<TextView>(R.id.home_item_title)
    val imageView: ImageView = itemView.findViewById<ImageView>(R.id.home_item_image)
}
