package com.think.design.card

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment

class CardSwipeDismissFragment : DemoFragment() {
    override fun getDemoTitleResId(): Int {
        return R.string.cat_card_swipe_dismiss
    }

    /** Inflate fragment view and setup with [SwipeDismissBehavior]  */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(R.layout.cat_card_swipe_fragment, viewGroup, false)
        val container = view.findViewById<CoordinatorLayout>(R.id.card_container)
        val swipeDismissBehavior = SwipeDismissBehavior<View?>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        val cardContentLayout = view.findViewById<MaterialCardView>(R.id.card_content_layout)
        val coordinatorParams =
            cardContentLayout.getLayoutParams() as CoordinatorLayout.LayoutParams

        coordinatorParams.setBehavior(swipeDismissBehavior)

        val dismissListener = createDismissListener(container, cardContentLayout)
        swipeDismissBehavior.setListener(dismissListener)

        setDismissOnKeyListener(cardContentLayout, dismissListener)
        setDismissAccessibilityDelegate(cardContentLayout, dismissListener)

        return view
    }

    private fun createDismissListener(
        container: CoordinatorLayout, cardContentLayout: MaterialCardView
    ): SwipeDismissBehavior.OnDismissListener {
        return object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View?) {
                Snackbar.make(container, R.string.cat_card_dismissed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(
                        R.string.cat_card_undo,
                        View.OnClickListener { v: View? -> resetCard(cardContentLayout) })
                    .show()
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(state, cardContentLayout)
            }
        }
    }

    private fun setDismissOnKeyListener(
        cardContentLayout: MaterialCardView, dismissListener: SwipeDismissBehavior.OnDismissListener
    ) {
        cardContentLayout.setOnKeyListener(
            View.OnKeyListener { view: View?, keyCode: Int, event: KeyEvent? ->
                if (event!!.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    dismissCard(requireView(), dismissListener)
                    return@OnKeyListener true
                }
                false
            })
    }

    private fun setDismissAccessibilityDelegate(
        cardContentLayout: MaterialCardView, dismissListener: SwipeDismissBehavior.OnDismissListener
    ) {
        ViewCompat.setAccessibilityDelegate(
            cardContentLayout,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View, info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.addAction(
                        AccessibilityActionCompat(
                            AccessibilityNodeInfoCompat.ACTION_DISMISS,
                            host.getResources().getString(R.string.cat_card_dismiss_action)
                        )
                    )
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle?
                ): Boolean {
                    if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS) {
                        dismissCard(host, dismissListener)
                        return true
                    }
                    return super.performAccessibilityAction(host, action, args)
                }
            })
    }

    private fun dismissCard(card: View, dismissListener: SwipeDismissBehavior.OnDismissListener) {
        if (card.getVisibility() == View.VISIBLE) {
            dismissListener.onDismiss(card)
            card.setVisibility(View.GONE)
        }
    }

    companion object {
        private fun onDragStateChanged(state: Int, cardContentLayout: MaterialCardView) {
            when (state) {
                SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> cardContentLayout.setDragged(
                    true
                )

                SwipeDismissBehavior.STATE_IDLE -> cardContentLayout.setDragged(false)
                else -> {}
            }
        }

        private fun resetCard(cardContentLayout: MaterialCardView) {
            val params = cardContentLayout
                .getLayoutParams() as CoordinatorLayout.LayoutParams
            params.setMargins(0, 0, 0, 0)
            cardContentLayout.setAlpha(1.0f)
            cardContentLayout.setVisibility(View.VISIBLE)
            cardContentLayout.requestLayout()
        }
    }
}
