package com.think.design.card

import androidx.annotation.LayoutRes
import com.think.design.R

class CardRichMediaDemoFragment : CardMainDemoFragment() {
    override fun getDemoTitleResId(): Int {
        return R.string.cat_card_rich_media_demo
    }

    @get:LayoutRes
    override val cardContent: Int
        get() = R.layout.cat_card_rich_media_demo_fragment
}
