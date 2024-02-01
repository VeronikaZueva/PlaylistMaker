package com.iclean.playlistmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val trackName : TextView = itemView.findViewById(R.id.track_name)
    private val artistName : TextView = itemView.findViewById(R.id.artist_name)
    private val trackTime : TextView = itemView.findViewById(R.id.track_time)

    fun bind(model : Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = model.trackTime
    }
}