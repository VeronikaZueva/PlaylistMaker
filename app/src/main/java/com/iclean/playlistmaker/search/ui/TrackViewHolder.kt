package com.iclean.playlistmaker.search.ui


import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.iclean.playlistmaker.R
import com.iclean.playlistmaker.databinding.TrackViewBinding
import com.iclean.playlistmaker.general.TrackMethods
import com.iclean.playlistmaker.search.domain.models.Track


class TrackViewHolder(private val binding: TrackViewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val trackMethods = TrackMethods()
    private val trackUrl : ImageView = itemView.findViewById(R.id.poster)
    private val placeholder = R.drawable.placeholder

    fun bind(model : Track) = with(binding) {
        trackId.text = model.trackId
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = trackMethods.dateFormatTrack(model.trackTimeMillis)
        val trackImage = model.artworkUrl100
        val context = itemView.context

        trackMethods.setImage(context, trackImage, trackUrl, placeholder, 2.0f)
    }

}