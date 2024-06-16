package com.iclean.playlistmaker.data.dto

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class GlideTrack {
    //Работа с обложкой изображения
    fun setImage(itemView: Context, trackImage: String?, trackUrl: ImageView, placeholder:Int, dp: Float) {
        Glide.with(itemView)
            .load(trackImage)
            .placeholder(placeholder)
            .transform(RoundedCorners(dpToPx(itemView, dp)))
            .into(trackUrl)
    }

    //Перевод значений в пиксели
    private fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()

    }
}