package com.iclean.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HistoryAdapter (private val historyList : ArrayList<TrackResponse.Track>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(historyList[position])

    }

    override fun getItemCount(): Int {
        val limit = if(historyList.size > 10) {
            10
        } else {
            historyList.size
        }
        return limit
    }

}