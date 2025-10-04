package com.code.think

import android.content.Context
import com.code.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * author : Samuel
 * e-mail : xingtai.wei@dreamsmart.com
 * time   : 2025/10/4 15:51
 * desc   :
 */
@HiltAndroidApp
class MainApplication: BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}