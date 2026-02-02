package com.think.design.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.insets.Protection
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.RelativePadding
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.windowpreferences.WindowPreferencesManager

class BottomSheetScrollableContentDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(this.demoContent, viewGroup, false /* attachToRoot */)
        val button = view.findViewById<View>(R.id.bottomsheet_button)
        button.setOnClickListener(View.OnClickListener { v: View? ->
            BottomSheet().show(
                getParentFragmentManager(),
                ""
            )
        })
        return view
    }

    @get:LayoutRes
    protected val demoContent: Int
        get() = R.layout.cat_bottomsheet_additional_demo_fragment

    /** A custom bottom sheet dialog fragment.  */
    class BottomSheet : BottomSheetDialogFragment() {
        @SuppressLint("RestrictedApi")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Set up BottomSheetDialog
            val bottomSheetDialog =
                BottomSheetDialog(
                    requireContext(), R.style.ThemeOverlay_Catalog_BottomSheetDialog_Scrollable
                )
            WindowPreferencesManager(requireContext())
                .applyEdgeToEdgePreference(bottomSheetDialog.getWindow())
            val content =
                LayoutInflater.from(getContext())
                    .inflate(
                        R.layout.cat_bottomsheet_scrollable_content,
                        FrameLayout(requireContext())
                    )
            bottomSheetDialog.setContentView(content)
            bottomSheetDialog.getBehavior().setPeekHeight(400)

            val bottomSheetContent = content.findViewById<View>(R.id.bottom_drawer_2)
            ViewUtils.doOnApplyWindowInsets(
                bottomSheetContent,
                ViewUtils.OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat?, initialPadding: RelativePadding? ->
                    // Add the inset in the inner NestedScrollView instead to make the edge-to-edge behavior
                    // consistent - i.e., the extra padding will only show at the bottom of all content,
                    // i.e.,
                    // only when you can no longer scroll down to show more content.
                    bottomSheetContent.setPaddingRelative(
                        initialPadding!!.start,
                        initialPadding.top,
                        initialPadding.end,
                        initialPadding.bottom
                                + insets!!.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                    )
                    insets
                })
            bottomSheetDialog.setProtections(
                mutableListOf<Protection?>(
                    BottomSheetBehavior.getDefaultBottomGradientProtection(requireContext())
                )
            )
            return bottomSheetDialog
        }
    }
}
