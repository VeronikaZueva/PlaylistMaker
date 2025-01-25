package com.iclean.playlistmaker.general

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

}