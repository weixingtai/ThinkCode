package com.think.design.adaptive

import androidx.fragment.app.Fragment
import com.think.design.musicplayer.MusicPlayerDemoActivity

class AdaptiveMusicPlayerDemoActivity : MusicPlayerDemoActivity() {
    override fun getLibraryDemoFragment(): Fragment {
        return AdaptiveMusicPlayerLibraryDemoFragment()
    }
}
