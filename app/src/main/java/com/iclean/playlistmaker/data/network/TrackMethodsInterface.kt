@file:Suppress("UNREACHABLE_CODE")

package com.iclean.playlistmaker.data.network

import android.content.Context
import android.widget.ImageView
import com.iclean.playlistmaker.ITunesApi

interface TrackMethodsInterface {

    fun dateFormatTrack(model : String?) : String? {
        return TODO("Provide the return value")
    }

    fun responseDataRetrofit(): ITunesApi? {
        return TODO("Provide the return value")
    }

    fun setImage(itemView: Context, trackImage: String?, trackUrl: ImageView, placeholder:Int, dp: Float) {}


}