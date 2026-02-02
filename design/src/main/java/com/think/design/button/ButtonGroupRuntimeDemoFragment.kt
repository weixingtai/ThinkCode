package com.think.design.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonGroup
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.think.design.R
import com.think.design.feature.DemoFragment

class ButtonGroupRuntimeDemoFragment : DemoFragment() {
    private val labels = arrayOfNulls<String>(MAX_COUNT)
    private val icons = arrayOfNulls<Drawable>(MAX_COUNT)
    private var buttonGroup: MaterialButtonGroup? = null
    private var buttonCount = 0
    private var addButton: Button? = null
    private var removeButton: Button? = null

    /**
     * Create a Demo View with [MaterialButtonGroup], in which, buttons are added and removed at
     * runtime.
     */
    override fun onCreateDemoView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view =
            layoutInflater.inflate(
                R.layout.cat_buttons_group_runtime_fragment, viewGroup,  /* attachToRoot= */false
            )
        val context = view.getContext()

        buttonGroup =
            view.findViewById<MaterialButtonGroup>(R.id.cat_dynamic_button_group_label_only)

        addButton = view.findViewById<Button>(R.id.cat_add_button)
        removeButton = view.findViewById<Button>(R.id.cat_remove_button)
        addButton!!.setOnClickListener(
            object : View.OnClickListener {
                @SuppressLint("SetTextI18n")
                override fun onClick(v: View?) {
                    addButton(context)
                    buttonCount++
                    updateControl()
                }
            })
        removeButton!!.setOnClickListener(
            View.OnClickListener { v: View? ->
                buttonGroup!!.removeViewAt(buttonCount - 1)
                buttonCount--
                updateControl()
            })

        updateControl()
        loadResources()

        val lastCheckedSwitch = view.findViewById<MaterialSwitch>(R.id.last_checked_switch)
        lastCheckedSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (ensureAtLeastOneButton()) {
                    val lastButton = buttonGroup!!.getChildAt(buttonCount - 1) as MaterialButton
                    lastButton.setChecked(isChecked)
                    lastButton.refreshDrawableState()
                }
            })
        val lastCheckableSwitch = view.findViewById<MaterialSwitch>(R.id.last_checkable_switch)
        lastCheckableSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (ensureAtLeastOneButton()) {
                    val lastButton = buttonGroup!!.getChildAt(buttonCount - 1) as MaterialButton
                    lastButton.setCheckable(isChecked)
                    lastButton.refreshDrawableState()
                    lastCheckedSwitch.setEnabled(isChecked)
                }
            })
        val enableSwitch = view.findViewById<MaterialSwitch>(R.id.last_enabled_switch)
        enableSwitch.setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                if (ensureAtLeastOneButton()) {
                    val lastButton = buttonGroup!!.getChildAt(buttonCount - 1) as MaterialButton
                    lastButton.setEnabled(isChecked)
                    lastButton.refreshDrawableState()
                }
            })

        return view
    }

    private fun ensureAtLeastOneButton(): Boolean {
        if (buttonCount == 0) {
            Snackbar.make(buttonGroup!!, "Add a button first.", Snackbar.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun addButton(context: Context) {
        val button = MaterialButton(context)
        button.setText(labels[buttonCount])
        button.setIcon(icons[buttonCount])
        button.setCheckable(true)
        button.setMaxLines(1)
        buttonGroup!!.addView(
            button,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
        )
        val lp =
            button.getLayoutParams() as MaterialButtonGroup.LayoutParams
        lp.overflowText = labels[buttonCount]
        lp.overflowIcon = icons[buttonCount]
    }

    private fun loadResources() {
        val labelsRes = getResources().obtainTypedArray(R.array.cat_button_group_dynamic_labels)
        for (i in 0..<MAX_COUNT) {
            labels[i] = labelsRes.getString(i % labelsRes.length())
        }
        labelsRes.recycle()
        val iconsRes = getResources().obtainTypedArray(R.array.cat_button_group_dynamic_icons)
        for (i in 0..<MAX_COUNT) {
            val iconId = iconsRes.getResourceId(i % iconsRes.length(), 0)
            if (iconId != 0) {
                icons[i] = getResources().getDrawable(iconId)
            }
        }
        iconsRes.recycle()
    }

    private fun updateControl() {
        if (buttonCount == 0) {
            removeButton!!.setEnabled(false)
        } else if (buttonCount == MAX_COUNT) {
            addButton!!.setEnabled(false)
        } else {
            addButton!!.setEnabled(true)
            removeButton!!.setEnabled(true)
        }
    }

    companion object {
        private const val MAX_COUNT = 10
    }
}
