package com.iclean.playlistmaker


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iclean.playlistmaker.data.TrackMethods


class TrackViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val trackMethods = TrackMethods()
    private val trackId : TextView = itemView.findViewById(R.id.track_id)
    private val trackName : TextView = itemView.findViewById(R.id.track_name)
    private val artistName : TextView = itemView.findViewById(R.id.artist_name)
    private val trackTime : TextView = itemView.findViewById(R.id.track_time)
    private val trackUrl : ImageView = itemView.findViewById(R.id.poster)
    private val placeholder = R.drawable.placeholder

    fun bind(model : TrackResponse.Track) {
        trackId.text = model.trackId
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = trackMethods.dateFormatTrack(model.trackTimeMillis)
        val trackImage = model.artworkUrl100
        val context = itemView.context

        trackMethods.setImage(context, trackImage, trackUrl, placeholder, 2.0f)

        //отработка нажатия
        itemView.setOnClickListener {
                    addTrack(model)
        }
    }

    private fun addTrack(model: TrackResponse.Track): TrackResponse.Track {
        return model
    }

}