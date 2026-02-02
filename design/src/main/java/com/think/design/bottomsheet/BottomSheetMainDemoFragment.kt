package com.think.design.bottomsheet

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.insets.Protection
import androidx.core.view.insets.ProtectionLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.materialswitch.MaterialSwitch
import com.think.design.R
import com.think.design.feature.DemoFragment
import com.think.design.windowpreferences.WindowPreferencesManager

class BottomSheetMainDemoFragment : DemoFragment() {
    private val persistentBottomSheetBackCallback: OnBackPressedCallback =
        object : OnBackPressedCallback( /* enabled= */false) {
            override fun handleOnBackStarted(backEvent: BackEventCompat) {
                persistentBottomSheetBehavior!!.startBackProgress(backEvent)
            }

            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                persistentBottomSheetBehavior!!.updateBackProgress(backEvent)
            }

            override fun handleOnBackPressed() {
                persistentBottomSheetBehavior!!.handleBackInvoked()
            }

            override fun handleOnBackCancelled() {
                persistentBottomSheetBehavior!!.cancelBackProgress()
            }
        }

    private var windowPreferencesManager: WindowPreferencesManager? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var persistentBottomSheetBehavior: BottomSheetBehavior<View?>? = null
    private var windowInsets: WindowInsetsCompat? = null
    private var peekHeightPx = 0

    private var fullScreenSwitch: MaterialSwitch? = null
    private var restrictExpansionSwitch: MaterialSwitch? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        windowPreferencesManager = WindowPreferencesManager(requireContext())
        peekHeightPx = getResources().getDimensionPixelSize(R.dimen.cat_bottom_sheet_peek_height)
    }

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(this.demoContent, viewGroup, false /* attachToRoot */)

        val content = view.findViewById<ViewGroup>(R.id.cat_bottomsheet_coordinator_layout)
        content.addView(layoutInflater.inflate(this.standardBottomSheetLayout, content, false))

        // Set up BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog!!.setContentView(R.layout.cat_bottomsheet_content)
        // Opt in to perform swipe to dismiss animation when dismissing bottom sheet dialog.
        bottomSheetDialog!!.setDismissWithAnimation(true)
        windowPreferencesManager!!.applyEdgeToEdgePreference(bottomSheetDialog!!.getWindow())
        val bottomSheetInternal =
            bottomSheetDialog!!.findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetDialog!!.setProtections(
            mutableListOf<Protection?>(
                BottomSheetBehavior.getDefaultBottomGradientProtection(requireContext())
            )
        )
        BottomSheetBehavior.from<View?>(bottomSheetInternal!!).setPeekHeight(peekHeightPx)
        val button = view.findViewById<View>(R.id.bottomsheet_button)
        button.setOnClickListener(
            View.OnClickListener { v: View? ->
                bottomSheetDialog!!.show()
                bottomSheetDialog!!.setTitle(getText(R.string.cat_bottomsheet_title))
                val button0 =
                    bottomSheetInternal.findViewById<Button>(R.id.cat_bottomsheet_modal_button)
                button0.setOnClickListener(
                    View.OnClickListener { v0: View? ->
                        Toast.makeText(
                            v!!.getContext(),
                            R.string.cat_bottomsheet_button_clicked,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    })

                val enabledSwitch =
                    bottomSheetInternal.findViewById<MaterialSwitch>(R.id.cat_bottomsheet_modal_enabled_switch)
                enabledSwitch.setOnCheckedChangeListener(
                    CompoundButton.OnCheckedChangeListener { buttonSwitch: CompoundButton?, isSwitchChecked: Boolean ->
                        val updatedText =
                            getText(
                                if (isSwitchChecked)
                                    R.string.cat_bottomsheet_button_label_enabled
                                else
                                    R.string.cat_bottomsheet_button_label_disabled
                            )
                        button0.setText(updatedText)
                        button0.setEnabled(isSwitchChecked)
                    })
            })

        fullScreenSwitch = view.findViewById<MaterialSwitch>(R.id.cat_fullscreen_switch)
        fullScreenSwitch!!.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                restrictExpansionSwitch!!.setEnabled(!isChecked)
                updateBottomSheetHeights()
            })

        restrictExpansionSwitch =
            view.findViewById<MaterialSwitch>(R.id.cat_bottomsheet_expansion_switch)
        restrictExpansionSwitch!!.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                fullScreenSwitch!!.setEnabled(!isChecked)
                view.findViewById<View?>(R.id.drag_handle)!!.setEnabled(!isChecked)
                bottomSheetInternal.findViewById<View?>(R.id.drag_handle)!!.setEnabled(!isChecked)
                updateBottomSheetHeights()
            })

        val dialogText = bottomSheetInternal.findViewById<TextView>(R.id.bottomsheet_state)
        BottomSheetBehavior.from<View?>(bottomSheetInternal)
            .addBottomSheetCallback(createBottomSheetCallback(dialogText))
        val bottomSheetText = view.findViewById<TextView>(R.id.cat_persistent_bottomsheet_state)
        val bottomSheetPersistent = view.findViewById<View>(R.id.bottom_drawer)
        val protectionLayout =
            view.findViewById<ProtectionLayout>(R.id.cat_bottomsheet_protection_layout)
        persistentBottomSheetBehavior = BottomSheetBehavior.from<View?>(bottomSheetPersistent)
        persistentBottomSheetBehavior!!.addBottomSheetCallback(
            createBottomSheetCallback(bottomSheetText)
        )
        bottomSheetPersistent.post(
            Runnable {
                val state = persistentBottomSheetBehavior!!.getState()
                updateStateTextView(bottomSheetPersistent, bottomSheetText, state)
                updateBackHandlingEnabled(state)
                protectionLayout.setProtections(
                    mutableListOf<Protection?>(
                        BottomSheetBehavior.getDefaultBottomGradientProtection(
                            requireContext()
                        )
                    )
                )
            })
        setupBackHandling(persistentBottomSheetBehavior!!)

        val button1 = view.findViewById<Button>(R.id.cat_bottomsheet_button)
        button1.setOnClickListener(
            View.OnClickListener { v: View? ->
                Toast.makeText(
                    v!!.getContext(), R.string.cat_bottomsheet_button_clicked, Toast.LENGTH_SHORT
                )
                    .show()
            })

        val enabledSwitch = view.findViewById<MaterialSwitch>(R.id.cat_bottomsheet_enabled_switch)
        enabledSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                val updatedText =
                    getText(
                        if (isChecked)
                            R.string.cat_bottomsheet_button_label_enabled
                        else
                            R.string.cat_bottomsheet_button_label_disabled
                    )
                button1.setText(updatedText)
                button1.setEnabled(isChecked)
            })

        ViewCompat.setOnApplyWindowInsetsListener(
            view,
            OnApplyWindowInsetsListener { ignored: View?, insets: WindowInsetsCompat? ->
                windowInsets = insets
                updateBottomSheetHeights()
                insets!!
            })

        return view
    }

    private val bottomSheetDialogDefaultHeight: Int
        get() = this.windowHeight * 2 / 3

    private val bottomSheetPersistentDefaultHeight: Int
        get() = this.windowHeight * 3 / 5

    private fun updateBottomSheetHeights() {
        val view = getView()
        val bottomSheetChildView = view!!.findViewById<View>(R.id.bottom_drawer)
        val params = bottomSheetChildView.getLayoutParams()
        val bottomSheetBehavior = BottomSheetBehavior.from<View?>(bottomSheetChildView)
        bottomSheetBehavior.setUpdateImportantForAccessibilityOnSiblings(fullScreenSwitch!!.isChecked())
        val modalBottomSheetChildView =
            bottomSheetDialog!!.findViewById<View?>(R.id.bottom_drawer_2)
        val layoutParams = modalBottomSheetChildView!!.getLayoutParams()
        val modalBottomSheetBehavior = bottomSheetDialog!!.getBehavior()
        var fitToContents = true
        var halfExpandedRatio = 0.5f
        val windowHeight = this.windowHeight
        if (params != null && layoutParams != null) {
            if (fullScreenSwitch!!.isEnabled() && fullScreenSwitch!!.isChecked()) {
                params.height = windowHeight
                layoutParams.height = windowHeight
                fitToContents = false
                halfExpandedRatio = 0.7f
            } else if (restrictExpansionSwitch!!.isEnabled() && restrictExpansionSwitch!!.isChecked()) {
                params.height = peekHeightPx
                layoutParams.height = peekHeightPx
            } else {
                params.height = this.bottomSheetPersistentDefaultHeight
                layoutParams.height = this.bottomSheetDialogDefaultHeight
            }
            bottomSheetChildView.setLayoutParams(params)
            modalBottomSheetChildView.setLayoutParams(layoutParams)
            bottomSheetBehavior.setFitToContents(fitToContents)
            modalBottomSheetBehavior.setFitToContents(fitToContents)
            bottomSheetBehavior.setHalfExpandedRatio(halfExpandedRatio)
            modalBottomSheetBehavior.setHalfExpandedRatio(halfExpandedRatio)
        }
    }

    private val windowHeight: Int
        get() {
            // Calculate window height for fullscreen use
            val displayMetrics = DisplayMetrics()
            (getContext() as Activity).getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics)
            // Allow Fullscreen BottomSheet to expand beyond system windows and draw under status bar.
            var height = displayMetrics.heightPixels
            if (windowInsets != null) {
                height += windowInsets!!.getSystemWindowInsetTop()
                height += windowInsets!!.getSystemWindowInsetBottom()
            }
            return height
        }

    @get:LayoutRes
    protected val demoContent: Int
        get() = R.layout.cat_bottomsheet_fragment

    @get:LayoutRes
    protected val standardBottomSheetLayout: Int
        get() = R.layout.cat_bottomsheet_standard

    private fun createBottomSheetCallback(text: TextView): BottomSheetCallback {
        return object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                updateStateTextView(bottomSheet, text, newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        }
    }

    private fun updateStateTextView(bottomSheet: View, text: TextView, state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_DRAGGING -> text.setText(R.string.cat_bottomsheet_state_dragging)
            BottomSheetBehavior.STATE_EXPANDED -> text.setText(R.string.cat_bottomsheet_state_expanded)
            BottomSheetBehavior.STATE_COLLAPSED -> text.setText(R.string.cat_bottomsheet_state_collapsed)
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                val bottomSheetBehavior =
                    BottomSheetBehavior.from<View?>(bottomSheet)
                text.setText(
                    getString(
                        R.string.cat_bottomsheet_state_half_expanded,
                        bottomSheetBehavior.getHalfExpandedRatio()
                    )
                )
            }

            else -> {}
        }
    }

    private fun setupBackHandling(behavior: BottomSheetBehavior<View?>) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, persistentBottomSheetBackCallback)
        behavior.addBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    updateBackHandlingEnabled(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
    }

    private fun updateBackHandlingEnabled(state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_EXPANDED, BottomSheetBehavior.STATE_HALF_EXPANDED -> persistentBottomSheetBackCallback.isEnabled =
                true

            BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_HIDDEN -> persistentBottomSheetBackCallback.isEnabled =
                false

            BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> {}
            else -> {}
        }
    }
}
