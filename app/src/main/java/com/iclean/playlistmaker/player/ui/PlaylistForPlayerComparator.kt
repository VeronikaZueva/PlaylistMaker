package com.iclean.playlistmaker.player.ui

import androidx.recyclerview.widget.DiffUtil
import com.iclean.playlistmaker.create.domain.models.Playlist


class PlaylistForPlayerComparator : DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem == newItem
    }
}