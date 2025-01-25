package com.iclean.playlistmaker.playlist.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.iclean.playlistmaker.databinding.TrackViewBinding
import com.iclean.playlistmaker.playlist.domain.api.TrackClick
import com.iclean.playlistmaker.search.domain.models.Track
import com.iclean.playlistmaker.search.ui.ItemComparator
import com.iclean.playlistmaker.search.ui.TrackViewHolder

class PlaylistItemAdapter(private val trackClick: TrackClick) : ListAdapter<Track, TrackViewHolder>(
    ItemComparator()
) {
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
        holder.itemView.setOnLongClickListener {
                trackClick.removeTrack(trackItem.trackId.toInt())
                true
        }

    }
}