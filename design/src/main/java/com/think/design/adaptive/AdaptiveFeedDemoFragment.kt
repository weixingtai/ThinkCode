package com.think.design.adaptive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ReactiveGuide
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R
import com.think.design.adaptive.AdaptiveFeedDemoFragment.FeedAdapter.FeedViewHolder

class AdaptiveFeedDemoFragment : Fragment() {
    private var fold: ReactiveGuide? = null
    private var constraintLayout: ConstraintLayout? = null
    private var closedLayout: ConstraintSet? = null

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.cat_adaptive_feed_fragment, viewGroup, false)
        fold = view.findViewById<ReactiveGuide>(R.id.fold)
        // Set up content lists.
        val smallContentList = view.findViewById<RecyclerView>(R.id.small_content_list)
        setUpContentRecyclerView(smallContentList,  /* isSmallContent= */true, 15)
        val largeContentList = view.findViewById<RecyclerView>(R.id.large_content_list)
        setUpContentRecyclerView(largeContentList,  /* isSmallContent= */false, 5)
        // Set up constraint sets.
        constraintLayout = view.findViewById<ConstraintLayout?>(R.id.feed_constraint_layout)
        closedLayout = ConstraintSet()
        closedLayout!!.clone(constraintLayout)
        return view
    }

    /* Sets up a recycler view with either small or large items list. */
    private fun setUpContentRecyclerView(
        recyclerView: RecyclerView, isSmallContent: Boolean, itemCount: Int
    ) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        val feedAdapter =
            FeedAdapter(
                if (isSmallContent)
                    R.layout.cat_adaptive_feed_small_item
                else
                    R.layout.cat_adaptive_feed_large_item,
                itemCount
            )
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setAdapter(feedAdapter)
        ViewCompat.setNestedScrollingEnabled(recyclerView,  /* enabled= */false)
    }

    /* Returns the constraint set to be used for the open layout configuration. */
    private fun getOpenLayout(closedLayout: ConstraintSet, foldWidth: Int): ConstraintSet {
        val marginHorizontal =
            getResources().getDimensionPixelOffset(R.dimen.cat_adaptive_margin_horizontal)
        val constraintSet = ConstraintSet()
        constraintSet.clone(closedLayout)
        // Change top button to be on the right of the fold.
        constraintSet.connect(
            R.id.top_button,
            ConstraintSet.START,
            R.id.fold,
            ConstraintSet.END,
            marginHorizontal + foldWidth
        )
        // Change small content list to be on the right of the fold and below top button.
        constraintSet.connect(
            R.id.small_content_list,
            ConstraintSet.START,
            R.id.fold,
            ConstraintSet.END,
            marginHorizontal + foldWidth
        )
        constraintSet.connect(
            R.id.small_content_list, ConstraintSet.TOP, R.id.top_button, ConstraintSet.BOTTOM
        )
        constraintSet.setVisibility(R.id.highlight_content_card, View.GONE)
        constraintSet.setVisibility(R.id.large_content_list, View.VISIBLE)

        return constraintSet
    }

    /**
     * Applies the open layout configuration.
     *
     * @param foldPosition position of the fold
     * @param foldWidth width of the fold if it's a hinge
     */
    fun setOpenLayout(foldPosition: Int, foldWidth: Int) {
        val openLayout = getOpenLayout(closedLayout!!, foldWidth)
        openLayout.applyTo(constraintLayout)
        fold!!.setGuidelineEnd(foldPosition)
    }

    /* Applies the closed layout configuration. */
    fun setClosedLayout() {
        fold!!.setGuidelineEnd(0)
        closedLayout!!.applyTo(constraintLayout)
    }

    /** A RecyclerView adapter for the content lists of the feed.  */
    private class FeedAdapter(
        @field:LayoutRes @param:LayoutRes private val itemLayout: Int,
        private val itemCount: Int
    ) : RecyclerView.Adapter<FeedViewHolder?>() {
        /** Provides a reference to the views for each data item.  */
        internal class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false)
            return FeedViewHolder(view)
        }

        override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
            // Populate content. Empty for demo purposes.
        }

        override fun getItemCount(): Int {
            return itemCount
        }
    }
}
