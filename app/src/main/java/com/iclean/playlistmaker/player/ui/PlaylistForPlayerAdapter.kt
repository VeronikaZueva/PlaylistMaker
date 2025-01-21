package com.iclean.playlistmaker.player.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iclean.playlistmaker.create.domain.models.Playlist
import com.iclean.playlistmaker.databinding.PlaylistBottomViewBinding
import com.iclean.playlistmaker.media.ui.playlists.PlaylistComparator


    class PlaylistForPlayerAdapter : ListAdapter<Playlist, PlaylistForPlayerViewHolder>(PlaylistComparator()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistForPlayerViewHolder {
            val view = LayoutInflater.from(parent.context)
            return PlaylistForPlayerViewHolder(PlaylistBottomViewBinding.inflate(view, parent, false))
        }

        override fun onBindViewHolder(holder: PlaylistForPlayerViewHolder, position: Int) {
            val playlistItem = getItem(position)
            holder.bind(playlistItem)

        }

    }
