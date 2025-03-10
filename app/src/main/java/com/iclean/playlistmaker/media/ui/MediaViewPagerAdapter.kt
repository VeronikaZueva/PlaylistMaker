package com.iclean.playlistmaker.media.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iclean.playlistmaker.media.ui.favorite.FavoriteFragment
import com.iclean.playlistmaker.media.ui.playlists.PlaylistFragment


class MediaViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) { FavoriteFragment.newInstance() } else { PlaylistFragment.newInstance() }
        }
    }
