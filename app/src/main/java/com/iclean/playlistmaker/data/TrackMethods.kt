package com.iclean.playlistmaker.data

import android.content.Context
import android.widget.ImageView
import com.iclean.playlistmaker.ITunesApi
import com.iclean.playlistmaker.data.dto.DateFormat
import com.iclean.playlistmaker.data.dto.GlideTrack
import com.iclean.playlistmaker.data.dto.RetrofitTrack
import com.iclean.playlistmaker.data.network.TrackMethodsInterface

class TrackMethods : TrackMethodsInterface {

    private val retrofitClass = RetrofitTrack()
    private val glideClass = GlideTrack()
    private val dateFormat = DateFormat()

    override fun dateFormatTrack(model : String?) : String? {
        return dateFormat.dateFormatTrack(model)
    }

    override fun responseDataRetrofit() : ITunesApi? {
        return retrofitClass.responseDataRetrofit()
    }

    override fun setImage(itemView: Context, trackImage: String?, trackUrl: ImageView, placeholder:Int, dp: Float) {
        glideClass.setImage(itemView, trackImage, trackUrl, placeholder, dp)
    }

}