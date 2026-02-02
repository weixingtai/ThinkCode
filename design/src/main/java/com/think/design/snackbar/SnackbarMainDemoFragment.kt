package com.think.design.snackbar

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment

class SnackbarMainDemoFragment : DemoFragment() {
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.cat_snackbar_main_demo_fragment, viewGroup, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.coordinator_layout)

        // Default snackbar
        view.findViewById<View?>(R.id.default_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    Snackbar.make(
                        coordinatorLayout,
                        R.string.cat_snackbar_default_message,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                })

        // Snackbar with single line
        view.findViewById<View?>(R.id.single_line_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    Snackbar.make(
                        coordinatorLayout,
                        R.string.cat_snackbar_single_line_message,
                        Snackbar.LENGTH_SHORT
                    )
                        .setTextMaxLines(1)
                        .show()
                })

        // Snackbar with action
        view.findViewById<View?>(R.id.with_action_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    Snackbar.make(
                        coordinatorLayout,
                        R.string.cat_snackbar_with_action_message,
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(
                            R.string.cat_snackbar_action_title,
                            View.OnClickListener { a: View? -> })
                        .show()
                })

        // Snackbar with multiple lines
        view.findViewById<View?>(R.id.multi_line_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    Snackbar.make(
                        coordinatorLayout,
                        R.string.cat_snackbar_multi_line_message,
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(
                            R.string.cat_snackbar_action_title,
                            View.OnClickListener { a: View? -> })
                        .setTextMaxLines(5)
                        .setCloseIconVisible(true)
                        .show()
                })

        // Snackbar with custom shape
        view.findViewById<View?>(R.id.custom_shape_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    // Set a custom shape by updating the theme's snackbarStyle attribute to a
                    // custom style with a custom shapeAppearance. For demonstration, this is done
                    // on a single snackbar using a theme overlay but it is more typically done by
                    // setting the snackbarStyle on th main theme and customizing all snackbars app-wide.
                    val c: Context =
                        ContextThemeWrapper(
                            v!!.getContext(), R.style.ThemeOverlay_Catalog_SnackbarWithCustomShape
                        )
                    val snackbar = Snackbar.make(
                        c,
                        coordinatorLayout,
                        getString(R.string.cat_snackbar_custom_shape_message),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction("Done", View.OnClickListener { a: View? -> })
                        .setCloseIconVisible( /* visible= */true)
                        .setCloseIconResource(R.drawable.ic_cancel_24)
                        .setCloseIconTint(Color.GREEN)
                    // Setting the close icon to visible removes the snackbar layout's end padding. To
                    // customize this, get the snackbar's view and set the end padding to a value
                    // that fits nicely with the custom shape and icon.
                    val snackbarLayout = snackbar.getView()
                    snackbarLayout.setPaddingRelative(
                        snackbarLayout.getPaddingStart(),
                        snackbarLayout.getPaddingTop(),
                        getResources().getDimensionPixelSize(
                            R.dimen.cat_snackbar_custom_shape_end_padding
                        ),
                        snackbarLayout.getPaddingBottom()
                    )
                    snackbar.show()
                })

        // Snackbar with close icon
        view.findViewById<View?>(R.id.with_close_button)!!
            .setOnClickListener(
                View.OnClickListener { v: View? ->
                    Snackbar.make(
                        coordinatorLayout,
                        R.string.cat_snackbar_with_close_message,
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(
                            R.string.cat_snackbar_action_title,
                            View.OnClickListener { a: View? -> })
                        .setCloseIconVisible(true)
                        .show()
                })
    }
}
