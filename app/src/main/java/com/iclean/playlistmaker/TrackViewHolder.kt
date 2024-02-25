package com.iclean.playlistmaker

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


class TrackViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    private val trackName : TextView = itemView.findViewById(R.id.track_name)
    private val artistName : TextView = itemView.findViewById(R.id.artist_name)
    private val trackUrl : ImageView = itemView.findViewById(R.id.poster)
    private val trackTime : TextView = itemView.findViewById(R.id.track_time)

    fun bind(model : TrackResponse.Track) {
        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault())
            .format(model.trackTimeMillis.toInt())

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