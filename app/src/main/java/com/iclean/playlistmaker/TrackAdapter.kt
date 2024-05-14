package com.iclean.playlistmaker


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackAdapter (private val trackList : ArrayList<TrackResponse.Track>) : RecyclerView.Adapter<TrackViewHolder>() {

     var onItemClick : ((trackItem : TrackResponse.Track) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackItem = trackList[position]
        holder.bind(trackItem)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(trackItem)
        }

    }

    override fun getItemCount(): Int {
        return trackList.size
    }
}