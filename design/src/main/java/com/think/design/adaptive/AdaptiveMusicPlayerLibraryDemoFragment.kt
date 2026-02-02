package com.think.design.adaptive

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.core.view.ViewCompat
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.think.design.R
import com.think.design.musicplayer.MusicData.Album
import com.think.design.musicplayer.MusicPlayerLibraryDemoFragment
import java.util.concurrent.Executor

class AdaptiveMusicPlayerLibraryDemoFragment : MusicPlayerLibraryDemoFragment() {
    private var windowInfoTracker: WindowInfoTrackerCallbackAdapter? = null
    private val stateContainer: Consumer<WindowLayoutInfo> = StateContainer()
    private val handler = Handler(Looper.getMainLooper())
    private val executor =
        Executor { command: Runnable? -> handler.post(Runnable { handler.post(command!!) }) }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        val view = layoutInflater.inflate(getDemoLayoutResId(), viewGroup, false)
        windowInfoTracker =
            WindowInfoTrackerCallbackAdapter(
                WindowInfoTracker.getOrCreate(requireActivity())
            )
        return view
    }

    override fun onStart() {
        super.onStart()
        if (windowInfoTracker != null) {
            windowInfoTracker!!.addWindowLayoutInfoListener(
                this.requireActivity(),
                executor,
                stateContainer
            )
        }
    }

    override fun onStop() {
        super.onStop()
        if (windowInfoTracker != null) {
            windowInfoTracker!!.removeWindowLayoutInfoListener(stateContainer)
        }
    }

    override fun onAlbumClicked(view: View, album: Album) {
        val fragment =
            AdaptiveMusicPlayerAlbumDemoFragment.newInstance(album.getAlbumId())

        val transform =
            MaterialContainerTransform(requireContext(),  /* entering= */true)
        fragment.setSharedElementEnterTransition(transform)

        // Use a Hold transition to keep this fragment visible beneath the MaterialContainerTransform
        // that transitions to the album details screen. Without a Hold, this fragment would disappear
        // as soon its container is replaced with a new Fragment.
        val hold = Hold()
        // Add root view as target for the Hold so that the entire view hierarchy is held in place as
        // one instead of each child view individually. Helps keep shadows during the transition.
        hold.addTarget(R.id.sliding_pane_layout)
        hold.setDuration(transform.getDuration())
        setExitTransition(hold)

        getParentFragmentManager()
            .beginTransaction()
            .addSharedElement(view, ViewCompat.getTransitionName(view)!!)
            .replace(R.id.fragment_container, fragment, AdaptiveMusicPlayerAlbumDemoFragment.TAG)
            .addToBackStack(AdaptiveMusicPlayerAlbumDemoFragment.TAG)
            .commit()
    }

    private inner class StateContainer : Consumer<WindowLayoutInfo> {
        override fun accept(windowLayoutInfo: WindowLayoutInfo) {
            val displayFeatures: MutableList<DisplayFeature?> = windowLayoutInfo.displayFeatures.toMutableList()
            val fadeThrough = MaterialFadeThrough()
            var listTypeGrid = false

            for (displayFeature in displayFeatures) {
                val foldingFeature = displayFeature as FoldingFeature
                if (foldingFeature.state == FoldingFeature.State.Companion.HALF_OPENED
                    || foldingFeature.state == FoldingFeature.State.Companion.FLAT
                ) {
                    // Device is foldable and open.
                    listTypeGrid = true
                }
            }

            setListType(listTypeGrid, fadeThrough)
        }
    }
}
