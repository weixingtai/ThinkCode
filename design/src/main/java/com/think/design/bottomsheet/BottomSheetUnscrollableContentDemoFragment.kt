package com.think.design.bottomsheet

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.RelativePadding
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.windowpreferences.WindowPreferencesManager

class BottomSheetUnscrollableContentDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
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
            val bottomSheetDialog = BottomSheetDialog(getContext()!!)
            WindowPreferencesManager(requireContext()).applyEdgeToEdgePreference(bottomSheetDialog.getWindow())
            bottomSheetDialog.setContentView(R.layout.cat_bottomsheet_unscrollable_content)
            val bottomSheetInternal =
                bottomSheetDialog.findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)

            // BottomSheetBehavior.from(bottomSheetInternal).setPeekHeight(400);
            val closeButton = bottomSheetDialog.findViewById<Button?>(R.id.close_icon)
            closeButton!!.setOnClickListener(View.OnClickListener { v: View? -> bottomSheetDialog.dismiss() })

            val bottomSheetContent = bottomSheetInternal!!.findViewById<View>(R.id.bottom_drawer_3)
            ViewUtils.doOnApplyWindowInsets(
                bottomSheetContent,
                ViewUtils.OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat?, initialPadding: RelativePadding? ->
                    // Add the inset in the inner NestedScrollView instead to make the edge-to-edge behavior
                    // consistent - i.e., the extra padding will only show at the bottom of all content, i.e.,
                    // only when you can no longer scroll down to show more content.
                    bottomSheetContent.setPaddingRelative(
                        initialPadding!!.start,
                        initialPadding.top,
                        initialPadding.end,
                        initialPadding.bottom + insets!!.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                    )
                    insets
                })
            return bottomSheetDialog
        }
    }
}
