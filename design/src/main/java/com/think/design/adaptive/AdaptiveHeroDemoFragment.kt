package com.think.design.adaptive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R
import com.think.design.adaptive.AdaptiveHeroDemoFragment.HeroAdapter.HeroViewHolder

class AdaptiveHeroDemoFragment : Fragment() {
    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.cat_adaptive_hero_fragment, viewGroup, false)
        val sideContentList = view.findViewById<RecyclerView>(R.id.hero_side_content)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        sideContentList.setLayoutManager(layoutManager)
        val adapter = HeroAdapter()
        sideContentList.setAdapter(adapter)
        ViewCompat.setNestedScrollingEnabled(sideContentList,  /* enabled= */false)

        // Set up constraint sets.
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.hero_constraint_layout)
        val smallLayout = getSmallLayout(constraintLayout)
        val mediumLayout = getMediumLayout(smallLayout)
        val largeLayout = getLargeLayout(mediumLayout)

        val screenWidth = getResources().getConfiguration().screenWidthDp
        if (screenWidth < AdaptiveUtils.MEDIUM_SCREEN_WIDTH_SIZE) {
            smallLayout.applyTo(constraintLayout)
        } else if (screenWidth < AdaptiveUtils.LARGE_SCREEN_WIDTH_SIZE) {
            mediumLayout.applyTo(constraintLayout)
        } else {
            largeLayout.applyTo(constraintLayout)
        }

        return view
    }

    /* Returns the constraint set to be used for the small layout configuration. */
    private fun getSmallLayout(constraintLayout: ConstraintLayout): ConstraintSet {
        val constraintSet = ConstraintSet()
        // Use the constraint set from the constraint layout.
        constraintSet.clone(constraintLayout)
        return constraintSet
    }

    /* Returns the constraint set to be used for the medium layout configuration. */
    private fun getMediumLayout(smallLayout: ConstraintSet): ConstraintSet {
        val marginHorizontal =
            getResources().getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin)
        val noMargin = getResources().getDimensionPixelOffset(R.dimen.cat_adaptive_margin_none)
        val marginHorizontalAdditional =
            getResources()
                .getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin_horizontal_additional)

        val constraintSet = ConstraintSet()
        constraintSet.clone(smallLayout)

        // Main content.
        constraintSet.connect(
            R.id.hero_main_content, ConstraintSet.TOP, R.id.hero_top_content, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            R.id.hero_main_content,
            ConstraintSet.END,
            R.id.hero_side_content_container,
            ConstraintSet.START
        )
        constraintSet.connect(
            R.id.hero_main_content,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.START, noMargin)
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.LEFT, noMargin)
        constraintSet.setMargin(
            R.id.hero_top_content,
            ConstraintSet.END,
            marginHorizontalAdditional
        )
        constraintSet.setMargin(
            R.id.hero_top_content,
            ConstraintSet.RIGHT,
            marginHorizontalAdditional
        )

        // Side content.
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.TOP,
            R.id.hero_top_content,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.START,
            R.id.hero_main_content,
            ConstraintSet.END
        )
        constraintSet.constrainPercentWidth(R.id.hero_side_content_container, 0.4f)

        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.START, marginHorizontal
        )
        constraintSet.setMargin(
            R.id.hero_side_content_container,
            ConstraintSet.LEFT,
            marginHorizontal
        )

        // Add more margin to the right/end of the side content to make sure there is a 24dp margin on
        // the right/end of the side content.
        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.RIGHT, marginHorizontalAdditional
        )
        constraintSet.setMargin(
            R.id.hero_side_content_container, ConstraintSet.END, marginHorizontalAdditional
        )
        return constraintSet
    }

    /* Returns the constraint set to be used for the large layout configuration. */
    private fun getLargeLayout(mediumLayout: ConstraintSet): ConstraintSet {
        val noMargin = getResources().getDimensionPixelOffset(R.dimen.cat_adaptive_margin_none)
        val marginHorizontal =
            getResources().getDimensionPixelOffset(R.dimen.cat_adaptive_hero_margin)

        val constraintSet = ConstraintSet()
        constraintSet.clone(mediumLayout)
        // Hero container.
        constraintSet.connect(
            R.id.hero_top_content,
            ConstraintSet.END,
            R.id.hero_side_content_container,
            ConstraintSet.START
        )

        // Side content.
        constraintSet.connect(
            R.id.hero_side_content_container,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        // Remove the margin from the main content since it no longer is at the right/end side.
        constraintSet.setMargin(R.id.hero_main_content, ConstraintSet.RIGHT, noMargin)
        constraintSet.setMargin(R.id.hero_main_content, ConstraintSet.END, noMargin)

        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.RIGHT, marginHorizontal)
        constraintSet.setMargin(R.id.hero_top_content, ConstraintSet.END, marginHorizontal)
        return constraintSet
    }

    /** A RecyclerView adapter for the side content list of the hero demo.  */
    private class HeroAdapter : RecyclerView.Adapter<HeroViewHolder?>() {
        /** Provides a reference to the views for each data item.  */
        internal class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
            val view =
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cat_adaptive_hero_item, parent, false)
            return HeroViewHolder(view)
        }

        override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
            // Populate content. Empty for demo purposes.
        }

        override fun getItemCount(): Int {
            return 10
        }
    }
}
