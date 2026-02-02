package com.think.design.adaptive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R
import com.think.design.musicplayer.MusicPlayerAlbumDemoFragment

class AdaptiveMusicPlayerAlbumDemoFragment : MusicPlayerAlbumDemoFragment() {
    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.cat_adaptive_music_player_album_fragment, viewGroup, false
        )
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        val container = view.findViewById<ViewGroup>(R.id.container)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val albumImage = view.findViewById<ImageView>(R.id.album_image)
        val albumTitle = view.findViewById<TextView>(R.id.album_title)
        val albumArtist = view.findViewById<TextView>(R.id.album_artist)
        val songRecyclerView = view.findViewById<RecyclerView>(R.id.song_recycler_view)

        setUpAlbumViews(container, toolbar, albumImage, albumTitle, albumArtist, songRecyclerView)
    }

    companion object {
        const val TAG: String = "AdaptiveMusicPlayerAlbumDemoFragment"
        private const val ALBUM_ID_KEY = "album_id_key"

        fun newInstance(albumId: Long): AdaptiveMusicPlayerAlbumDemoFragment {
            val fragment = AdaptiveMusicPlayerAlbumDemoFragment()
            val bundle = Bundle()
            bundle.putLong(ALBUM_ID_KEY, albumId)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}
