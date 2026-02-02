package com.think.design.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.card.MaterialCardView
import com.think.design.R
import com.think.design.feature.DemoFragment

/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */class CardStatesFragment : DemoFragment() {
    override fun getDemoTitleResId(): Int {
        return R.string.cat_card_states
    }

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater
            .inflate(R.layout.cat_card_states_fragment, viewGroup, false /* attachToRoot */)
        val radioGroup = view.findViewById<RadioGroup>(R.id.cat_card_radio_group)
        val card = view.findViewById<MaterialCardView>(R.id.card)
        val checkableCard = view.findViewById<MaterialCardView>(R.id.checkable_card)

        checkableCard.setOnLongClickListener(
            OnLongClickListener { v: View? ->
                checkableCard.setChecked(!checkableCard.isChecked())
                true
            })

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
                card.setHovered(checkedId == R.id.hovered)
                card.setPressed(checkedId == R.id.pressed)
                checkableCard.setHovered(checkedId == R.id.hovered)
                checkableCard.setPressed(checkedId == R.id.pressed)
            })

        return view
    }
}
