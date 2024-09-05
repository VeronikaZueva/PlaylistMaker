package com.iclean.playlistmaker.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iclean.playlistmaker.databinding.TrackViewBinding
import com.iclean.playlistmaker.search.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track

class HistoryAdapter(private val trackClick : TrackClick) : ListAdapter<Track, TrackViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackViewBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackItem = getItem(position)
        holder.bind(trackItem)
        holder.itemView.setOnClickListener {
            trackClick.getTrack(trackItem)
        }

    }
}