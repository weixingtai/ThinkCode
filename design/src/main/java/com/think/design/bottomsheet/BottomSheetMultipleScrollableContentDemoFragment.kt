package com.think.design.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.windowpreferences.WindowPreferencesManager

class BottomSheetMultipleScrollableContentDemoFragment : DemoFragment() {
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
        private var adapter: BottomSheetViewPagerAdapter? = null

        /**
         * A simple fragment containing scrollable content, used as pages within the [ViewPager].
         */
        class ScrollableDemoFragment : Fragment() {
            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                return inflater.inflate(
                    R.layout.cat_bottomsheet_viewpager_page_content,
                    container,
                    false
                )
            }

            override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}
        }

        private inner class BottomSheetViewPagerAdapter(fm: FragmentManager) :
            FragmentStatePagerAdapter(fm) {
            override fun getItem(i: Int): Fragment {
                return ScrollableDemoFragment()
            }

            override fun getCount(): Int {
                return 5
            }
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Set up BottomSheetDialog
            adapter = BottomSheetViewPagerAdapter(getChildFragmentManager())
            val bottomSheetDialog =
                BottomSheetDialog(
                    getContext()!!, R.style.ThemeOverlay_Catalog_BottomSheetDialog_MultiScrollable
                )
            WindowPreferencesManager(requireContext())
                .applyEdgeToEdgePreference(bottomSheetDialog.getWindow())
            val content =
                LayoutInflater.from(getContext())
                    .inflate(
                        R.layout.cat_bottomsheet_viewpager_content,
                        FrameLayout(getContext()!!)
                    )
            bottomSheetDialog.setContentView(content)
            bottomSheetDialog.getBehavior().setPeekHeight(400)
            val viewPager = content.findViewById<ViewPager>(R.id.cat_bottom_sheet_viewpager)
            viewPager.setAdapter(adapter)
            return bottomSheetDialog
        }
    }
}
