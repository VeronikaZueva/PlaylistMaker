package com.iclean.playlistmaker

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val trackName : TextView = itemView.findViewById(R.id.track_name)
    private val artistName : TextView = itemView.findViewById(R.id.artist_name)
    private val trackTime : TextView = itemView.findViewById(R.id.track_time)
    private val trackUrl : ImageView = itemView.findViewById(R.id.poster)

    fun bind(model : Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = model.trackTime

        Glide.with(itemView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(dpToPx(itemView)))
            .into(trackUrl)
    }

   private fun dpToPx(context: View): Int {
       val dp = 2.0f
       return TypedValue.applyDimension(
           TypedValue.COMPLEX_UNIT_DIP,
           dp,
           context.resources.displayMetrics).toInt()
   }



}