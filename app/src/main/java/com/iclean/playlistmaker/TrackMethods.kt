package com.iclean.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

open class TrackMethods   {

    //Запрос к API и Retrofit
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val itunesService: ITunesApi? = retrofit.create(ITunesApi::class.java)

    //Работа с датой и временем
    fun dateFormatTrack(model : String?) : String? {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault())
            .format(model?.toInt())
    }

    //Работа с обложкой изображения
    fun setImage(itemView: Context, trackImage: String?, trackUrl: ImageView, placeholder:Int) {
        Glide.with(itemView)
            .load(trackImage)
            .placeholder(placeholder)
            .transform(RoundedCorners(dpToPx(itemView)))
            .into(trackUrl)
    }

    //Перевод значений в пиксели
    private fun dpToPx(context: Context): Int {
        val dp = 2.0f
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }


}