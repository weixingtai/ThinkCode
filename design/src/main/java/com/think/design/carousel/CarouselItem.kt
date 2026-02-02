package com.think.design.carousel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal class CarouselItem(
    @JvmField @field:DrawableRes @get:DrawableRes @param:DrawableRes val drawableRes: Int,
    @JvmField @field:StringRes @get:StringRes @param:StringRes val contentDescRes: Int
)
