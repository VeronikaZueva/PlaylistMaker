package com.iclean.playlistmaker.media.ui.playlists

import androidx.recyclerview.widget.DiffUtil
import com.iclean.playlistmaker.create.domain.models.Playlist


class PlaylistComparator: DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }
}