package com.iclean.playlistmaker.general

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistMethods {
    fun setImage(
        itemView: Context,
        trackImage: String?,
        trackUrl: ImageView,
        placeholder: Int,
        dp: Int
    ) {
        Glide.with(itemView).load(trackImage)
            .transform(CenterCrop(), RoundedCorners(dp))
            .placeholder(placeholder).into(trackUrl)
    }

    fun setCountTrack(countTrack: Int) : String {
        return if (countTrack % 10 == 1) {
            "$countTrack трек"
        } else {
            if ((countTrack % 10 == 2) or (countTrack % 10 == 3) or (countTrack % 10 == 4)) {
                "$countTrack трека"
            } else if ((countTrack == 11) or (countTrack == 12) or (countTrack == 13) or (countTrack == 14)) {
                "$countTrack треков"
            } else {
                "$countTrack треков"
            }
        }
    }

    fun dateFormatTrack(model : Int) : String {
        val time = SimpleDateFormat(
            "mm",
            Locale.getDefault())
            .format(model)
        return if(time.toInt() == 1) {"${time.substring(1)} минута"}
        else if((time.toInt() == 2) or (time.toInt() == 3) or (time.toInt() == 4)) {"${time.substring(1)} минуты"}
        else if((time.toInt() == 5) or (time.toInt() == 6) or (time.toInt() == 7) or (time.toInt() == 8) or (time.toInt() == 9)) {"${time.substring(1)} минут"}
        else if(time.toInt() % 10 == 1) {"$time минута"}
        else if((time.toInt() % 10 == 2) or (time.toInt() % 10 == 3) or (time.toInt() % 10 == 4)){"$time минуты"}
        else if((time.toInt() == 11) or (time.toInt() == 12) or (time.toInt() == 13) or (time.toInt() == 14)) {
            "$time минут"
        } else {"$time минут"}
    }

}