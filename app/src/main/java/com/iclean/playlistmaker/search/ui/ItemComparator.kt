package com.iclean.playlistmaker.search.ui

import androidx.recyclerview.widget.DiffUtil
import com.iclean.playlistmaker.search.domain.models.Track

class ItemComparator : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}